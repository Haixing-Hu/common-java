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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import javax.annotation.Nullable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ltd.qubit.commons.config.error.ConfigurationError;
import ltd.qubit.commons.config.impl.DefaultConfig;
import ltd.qubit.commons.io.serialize.XmlSerialization;
import ltd.qubit.commons.lang.SystemUtils;
import ltd.qubit.commons.text.xml.XmlException;

/**
 * Provides utility functions about the {@link Config} objects.
 *
 * @author Haixing Hu
 */
public final class ConfigUtils {

  private static final Logger LOGGER = LoggerFactory.getLogger(ConfigUtils.class);

  /**
   * Choose the non-null value of a specified value and a property value in a
   * {@link Config}.
   *
   * @param value
   *     the specified value, which could be {@code null}.
   * @param config
   *     the specified {@link Config}.
   * @param name
   *     the name of the specified property in the {@link Config}.
   * @return if the specified value is not {@code null}, returns the specified
   *     value; otherwise, returns the specified property value in the specified
   *     {@link Config}.
   * @throws ConfigurationError
   *     if there is no property with the specified name, or the specified
   *     property has no value, or the type of the specified property is not
   *     {@code boolean}.
   */
  public static boolean choose(@Nullable final Boolean value,
      final Config config, final String name) {
    if (value != null) {
      return value;
    } else {
      return config.getBoolean(name);
    }
  }

  /**
   * Choose the non-null value of a specified value and a property value in a
   * {@link Config}.
   *
   * @param value
   *     the specified value, which could be {@code null}.
   * @param config
   *     the specified {@link Config}.
   * @param name
   *     the name of the specified property in the {@link Config}.
   * @param defaultValue
   *     the default value to use if the specified {@link Config} has no
   *     property with the specified name or the specified property has no
   *     value.
   * @return if the specified value is not {@code null}, returns the specified
   *     value; otherwise, returns the specified property value in the specified
   *     {@link Config}; if there is no such property or the property has no
   *     value, returns the default value.
   */
  public static boolean choose(@Nullable final Boolean value,
      final Config config, final String name, final boolean defaultValue) {
    if (value != null) {
      return value;
    } else {
      return config.getBoolean(name, defaultValue);
    }
  }

  /**
   * Choose the non-null value of a specified value and a property value in a
   * {@link Config}.
   *
   * @param value
   *     the specified value, which could be {@code null}.
   * @param config
   *     the specified {@link Config}.
   * @param name
   *     the name of the specified property in the {@link Config}.
   * @return if the specified value is not {@code null}, returns the specified
   *     value; otherwise, returns the specified property value in the specified
   *     {@link Config}.
   * @throws ConfigurationError
   *     if there is no property with the specified name, or the specified
   *     property has no value, or the type of the specified property is not
   *     {@code char}.
   */
  public static char choose(@Nullable final Character value,
      final Config config, final String name) {
    if (value != null) {
      return value;
    } else {
      return config.getChar(name);
    }
  }

  /**
   * Choose the non-null value of a specified value and a property value in a
   * {@link Config}.
   *
   * @param value
   *     the specified value, which could be {@code null}.
   * @param config
   *     the specified {@link Config}.
   * @param name
   *     the name of the specified property in the {@link Config}.
   * @param defaultValue
   *     the default value to use if the specified {@link Config} has no
   *     property with the specified name or the specified property has no
   *     value.
   * @return if the specified value is not {@code null}, returns the specified
   *     value; otherwise, returns the specified property value in the specified
   *     {@link Config}; if there is no such property or the property has no
   *     value, returns the default value.
   */
  public static char choose(@Nullable final Character value,
      final Config config, final String name, final char defaultValue) {
    if (value != null) {
      return value;
    } else {
      return config.getChar(name, defaultValue);
    }
  }

  /**
   * Choose the non-null value of a specified value and a property value in a
   * {@link Config}.
   *
   * @param value
   *     the specified value, which could be {@code null}.
   * @param config
   *     the specified {@link Config}.
   * @param name
   *     the name of the specified property in the {@link Config}.
   * @return if the specified value is not {@code null}, returns the specified
   *     value; otherwise, returns the specified property value in the specified
   *     {@link Config}.
   * @throws ConfigurationError
   *     if there is no property with the specified name, or the specified
   *     property has no value, or the type of the specified property is not
   *     {@code byte}.
   */
  public static byte choose(@Nullable final Byte value, final Config config,
      final String name) {
    if (value != null) {
      return value;
    } else {
      return config.getByte(name);
    }
  }

  /**
   * Choose the non-null value of a specified value and a property value in a
   * {@link Config}.
   *
   * @param value
   *     the specified value, which could be {@code null}.
   * @param config
   *     the specified {@link Config}.
   * @param name
   *     the name of the specified property in the {@link Config}.
   * @param defaultValue
   *     the default value to use if the specified {@link Config} has no
   *     property with the specified name or the specified property has no
   *     value.
   * @return if the specified value is not {@code null}, returns the specified
   *     value; otherwise, returns the specified property value in the specified
   *     {@link Config}; if there is no such property or the property has no
   *     value, returns the default value.
   */
  public static byte choose(@Nullable final Byte value, final Config config,
      final String name, final byte defaultValue) {
    if (value != null) {
      return value;
    } else {
      return config.getByte(name, defaultValue);
    }
  }

  /**
   * Choose the non-null value of a specified value and a property value in a
   * {@link Config}.
   *
   * @param value
   *     the specified value, which could be {@code null}.
   * @param config
   *     the specified {@link Config}.
   * @param name
   *     the name of the specified property in the {@link Config}.
   * @return if the specified value is not {@code null}, returns the specified
   *     value; otherwise, returns the specified property value in the specified
   *     {@link Config}.
   * @throws ConfigurationError
   *     if there is no property with the specified name, or the specified
   *     property has no value, or the type of the specified property is not
   *     {@code short}.
   */
  public static short choose(@Nullable final Short value, final Config config,
      final String name) {
    if (value != null) {
      return value;
    } else {
      return config.getShort(name);
    }
  }

  /**
   * Choose the non-null value of a specified value and a property value in a
   * {@link Config}.
   *
   * @param value
   *     the specified value, which could be {@code null}.
   * @param config
   *     the specified {@link Config}.
   * @param name
   *     the name of the specified property in the {@link Config}.
   * @param defaultValue
   *     the default value to use if the specified {@link Config} has no
   *     property with the specified name or the specified property has no
   *     value.
   * @return if the specified value is not {@code null}, returns the specified
   *     value; otherwise, returns the specified property value in the specified
   *     {@link Config}; if there is no such property or the property has no
   *     value, returns the default value.
   */
  public static short choose(@Nullable final Short value, final Config config,
      final String name, final short defaultValue) {
    if (value != null) {
      return value;
    } else {
      return config.getShort(name, defaultValue);
    }
  }

  /**
   * Choose the non-null value of a specified value and a property value in a
   * {@link Config}.
   *
   * @param value
   *     the specified value, which could be {@code null}.
   * @param config
   *     the specified {@link Config}.
   * @param name
   *     the name of the specified property in the {@link Config}.
   * @return if the specified value is not {@code null}, returns the specified
   *     value; otherwise, returns the specified property value in the specified
   *     {@link Config}.
   * @throws ConfigurationError
   *     if there is no property with the specified name, or the specified
   *     property has no value, or the type of the specified property is not
   *     {@code int}.
   */
  public static int choose(@Nullable final Integer value, final Config config,
      final String name) {
    if (value != null) {
      return value;
    } else {
      return config.getInt(name);
    }
  }

  /**
   * Choose the non-null value of a specified value and a property value in a
   * {@link Config}.
   *
   * @param value
   *     the specified value, which could be {@code null}.
   * @param config
   *     the specified {@link Config}.
   * @param name
   *     the name of the specified property in the {@link Config}.
   * @param defaultValue
   *     the default value to use if the specified {@link Config} has no
   *     property with the specified name or the specified property has no
   *     value.
   * @return if the specified value is not {@code null}, returns the specified
   *     value; otherwise, returns the specified property value in the specified
   *     {@link Config}; if there is no such property or the property has no
   *     value, returns the default value.
   */
  public static int choose(@Nullable final Integer value, final Config config,
      final String name, final int defaultValue) {
    if (value != null) {
      return value;
    } else {
      return config.getInt(name, defaultValue);
    }
  }

  /**
   * Choose the non-null value of a specified value and a property value in a
   * {@link Config}.
   *
   * @param value
   *     the specified value, which could be {@code null}.
   * @param config
   *     the specified {@link Config}.
   * @param name
   *     the name of the specified property in the {@link Config}.
   * @return if the specified value is not {@code null}, returns the specified
   *     value; otherwise, returns the specified property value in the specified
   *     {@link Config}.
   * @throws ConfigurationError
   *     if there is no property with the specified name, or the specified
   *     property has no value, or the type of the specified property is not
   *     {@code long}.
   */
  public static long choose(@Nullable final Long value, final Config config,
      final String name) {
    if (value != null) {
      return value;
    } else {
      return config.getLong(name);
    }
  }

  /**
   * Choose the non-null value of a specified value and a property value in a
   * {@link Config}.
   *
   * @param value
   *     the specified value, which could be {@code null}.
   * @param config
   *     the specified {@link Config}.
   * @param name
   *     the name of the specified property in the {@link Config}.
   * @param defaultValue
   *     the default value to use if the specified {@link Config} has no
   *     property with the specified name or the specified property has no
   *     value.
   * @return if the specified value is not {@code null}, returns the specified
   *     value; otherwise, returns the specified property value in the specified
   *     {@link Config}; if there is no such property or the property has no
   *     value, returns the default value.
   */
  public static long choose(@Nullable final Long value, final Config config,
      final String name, final long defaultValue) {
    if (value != null) {
      return value;
    } else {
      return config.getLong(name, defaultValue);
    }
  }

  /**
   * Choose the non-null value of a specified value and a property value in a
   * {@link Config}.
   *
   * @param value
   *     the specified value, which could be {@code null}.
   * @param config
   *     the specified {@link Config}.
   * @param name
   *     the name of the specified property in the {@link Config}.
   * @return if the specified value is not {@code null}, returns the specified
   *     value; otherwise, returns the specified property value in the specified
   *     {@link Config}.
   * @throws ConfigurationError
   *     if there is no property with the specified name, or the specified
   *     property has no value, or the type of the specified property is not
   *     {@code float}.
   */
  public static float choose(@Nullable final Float value, final Config config,
      final String name) {
    if (value != null) {
      return value;
    } else {
      return config.getFloat(name);
    }
  }

  /**
   * Choose the non-null value of a specified value and a property value in a
   * {@link Config}.
   *
   * @param value
   *     the specified value, which could be {@code null}.
   * @param config
   *     the specified {@link Config}.
   * @param name
   *     the name of the specified property in the {@link Config}.
   * @param defaultValue
   *     the default value to use if the specified {@link Config} has no
   *     property with the specified name or the specified property has no
   *     value.
   * @return if the specified value is not {@code null}, returns the specified
   *     value; otherwise, returns the specified property value in the specified
   *     {@link Config}; if there is no such property or the property has no
   *     value, returns the default value.
   */
  public static float choose(@Nullable final Float value, final Config config,
      final String name, final float defaultValue) {
    if (value != null) {
      return value;
    } else {
      return config.getFloat(name, defaultValue);
    }
  }

  /**
   * Choose the non-null value of a specified value and a property value in a
   * {@link Config}.
   *
   * @param value
   *     the specified value, which could be {@code null}.
   * @param config
   *     the specified {@link Config}.
   * @param name
   *     the name of the specified property in the {@link Config}.
   * @return if the specified value is not {@code null}, returns the specified
   *     value; otherwise, returns the specified property value in the specified
   *     {@link Config}.
   * @throws ConfigurationError
   *     if there is no property with the specified name, or the specified
   *     property has no value, or the type of the specified property is not
   *     {@code double}.
   */
  public static double choose(@Nullable final Double value,
      final Config config, final String name) {
    if (value != null) {
      return value;
    } else {
      return config.getDouble(name);
    }
  }

  /**
   * Choose the non-null value of a specified value and a property value in a
   * {@link Config}.
   *
   * @param value
   *     the specified value, which could be {@code null}.
   * @param config
   *     the specified {@link Config}.
   * @param name
   *     the name of the specified property in the {@link Config}.
   * @param defaultValue
   *     the default value to use if the specified {@link Config} has no
   *     property with the specified name or the specified property has no
   *     value.
   * @return if the specified value is not {@code null}, returns the specified
   *     value; otherwise, returns the specified property value in the specified
   *     {@link Config}; if there is no such property or the property has no
   *     value, returns the default value.
   */
  public static double choose(@Nullable final Double value,
      final Config config, final String name, final double defaultValue) {
    if (value != null) {
      return value;
    } else {
      return config.getDouble(name, defaultValue);
    }
  }

  /**
   * Choose the non-null value of a specified value and a property value in a
   * {@link Config}.
   *
   * @param value
   *     the specified value, which could be {@code null}.
   * @param config
   *     the specified {@link Config}.
   * @param name
   *     the name of the specified property in the {@link Config}.
   * @return if the specified value is not {@code null}, returns the specified
   *     value; otherwise, returns the specified property value in the specified
   *     {@link Config}.
   * @throws ConfigurationError
   *     if there is no property with the specified name, or the specified
   *     property has no value, or the type of the specified property is not
   *     {@code string}.
   */
  public static String choose(@Nullable final String value,
      final Config config, final String name) {
    if (value != null) {
      return value;
    } else {
      return config.getString(name);
    }
  }

  /**
   * Choose the non-null value of a specified value and a property value in a
   * {@link Config}.
   *
   * @param value
   *     the specified value, which could be {@code null}.
   * @param config
   *     the specified {@link Config}.
   * @param name
   *     the name of the specified property in the {@link Config}.
   * @param defaultValue
   *     the default value to use if the specified {@link Config} has no
   *     property with the specified name or the specified property has no
   *     value.
   * @return if the specified value is not {@code null}, returns the specified
   *     value; otherwise, returns the specified property value in the specified
   *     {@link Config}; if there is no such property or the property has no
   *     value, returns the default value.
   */
  public static String choose(@Nullable final String value,
      final Config config, final String name, final String defaultValue) {
    if (value != null) {
      return value;
    } else {
      return config.getString(name, defaultValue);
    }
  }

  /**
   * Choose the non-null value of a specified value and a property value in a
   * {@link Config}.
   *
   * @param value
   *     the specified value, which could be {@code null}.
   * @param config
   *     the specified {@link Config}.
   * @param name
   *     the name of the specified property in the {@link Config}.
   * @return if the specified value is not {@code null}, returns the specified
   *     value; otherwise, returns the specified property value in the specified
   *     {@link Config}.
   * @throws ConfigurationError
   *     if there is no property with the specified name, or the specified
   *     property has no value, or the type of the specified property is not
   *     {@code Date}.
   */
  public static LocalDate choose(@Nullable final LocalDate value, final Config config,
      final String name) {
    if (value != null) {
      return value;
    } else {
      return config.getDate(name);
    }
  }

  /**
   * Choose the non-null value of a specified value and a property value in a
   * {@link Config}.
   *
   * @param value
   *     the specified value, which could be {@code null}.
   * @param config
   *     the specified {@link Config}.
   * @param name
   *     the name of the specified property in the {@link Config}.
   * @param defaultValue
   *     the default value to use if the specified {@link Config} has no
   *     property with the specified name or the specified property has no
   *     value.
   * @return if the specified value is not {@code null}, returns the specified
   *     value; otherwise, returns the specified property value in the specified
   *     {@link Config}; if there is no such property or the property has no
   *     value, returns the default value.
   */
  public static LocalDate choose(@Nullable final LocalDate value, final Config config,
      final String name, @Nullable final LocalDate defaultValue) {
    if (value != null) {
      return value;
    } else {
      return config.getDate(name, defaultValue);
    }
  }

  /**
   * Choose the non-null value of a specified value and a property value in a
   * {@link Config}.
   *
   * @param value
   *     the specified value, which could be {@code null}.
   * @param config
   *     the specified {@link Config}.
   * @param name
   *     the name of the specified property in the {@link Config}.
   * @return if the specified value is not {@code null}, returns the specified
   *     value; otherwise, returns the specified property value in the specified
   *     {@link Config}.
   * @throws ConfigurationError
   *     if there is no property with the specified name, or the specified
   *     property has no value, or the type of the specified property is not
   *     {@code Date}.
   */
  public static LocalTime choose(@Nullable final LocalTime value, final Config config,
      final String name) {
    if (value != null) {
      return value;
    } else {
      return config.getTime(name);
    }
  }

  /**
   * Choose the non-null value of a specified value and a property value in a
   * {@link Config}.
   *
   * @param value
   *     the specified value, which could be {@code null}.
   * @param config
   *     the specified {@link Config}.
   * @param name
   *     the name of the specified property in the {@link Config}.
   * @param defaultValue
   *     the default value to use if the specified {@link Config} has no
   *     property with the specified name or the specified property has no
   *     value.
   * @return if the specified value is not {@code null}, returns the specified
   *     value; otherwise, returns the specified property value in the specified
   *     {@link Config}; if there is no such property or the property has no
   *     value, returns the default value.
   */
  public static LocalTime choose(@Nullable final LocalTime value, final Config config,
      final String name, @Nullable final LocalTime defaultValue) {
    if (value != null) {
      return value;
    } else {
      return config.getTime(name, defaultValue);
    }
  }

  /**
   * Choose the non-null value of a specified value and a property value in a
   * {@link Config}.
   *
   * @param value
   *     the specified value, which could be {@code null}.
   * @param config
   *     the specified {@link Config}.
   * @param name
   *     the name of the specified property in the {@link Config}.
   * @return if the specified value is not {@code null}, returns the specified
   *     value; otherwise, returns the specified property value in the specified
   *     {@link Config}.
   * @throws ConfigurationError
   *     if there is no property with the specified name, or the specified
   *     property has no value, or the type of the specified property is not
   *     {@code Date}.
   */
  public static LocalDateTime choose(@Nullable final LocalDateTime value, final Config config,
      final String name) {
    if (value != null) {
      return value;
    } else {
      return config.getDateTime(name);
    }
  }

  /**
   * Choose the non-null value of a specified value and a property value in a
   * {@link Config}.
   *
   * @param value
   *     the specified value, which could be {@code null}.
   * @param config
   *     the specified {@link Config}.
   * @param name
   *     the name of the specified property in the {@link Config}.
   * @param defaultValue
   *     the default value to use if the specified {@link Config} has no
   *     property with the specified name or the specified property has no
   *     value.
   * @return if the specified value is not {@code null}, returns the specified
   *     value; otherwise, returns the specified property value in the specified
   *     {@link Config}; if there is no such property or the property has no
   *     value, returns the default value.
   */
  public static LocalDateTime choose(@Nullable final LocalDateTime value, final Config config,
      final String name, @Nullable final LocalDateTime defaultValue) {
    if (value != null) {
      return value;
    } else {
      return config.getDateTime(name, defaultValue);
    }
  }

  /**
   * Choose the non-null value of a specified value and a property value in a
   * {@link Config}.
   *
   * @param value
   *     the specified value, which could be {@code null}.
   * @param config
   *     the specified {@link Config}.
   * @param name
   *     the name of the specified property in the {@link Config}.
   * @return if the specified value is not {@code null}, returns the specified
   *     value; otherwise, returns the specified property value in the specified
   *     {@link Config}.
   * @throws ConfigurationError
   *     if there is no property with the specified name, or the specified
   *     property has no value, or the type of the specified property is not
   *     {@code BigInteger}.
   */
  public static BigInteger choose(@Nullable final BigInteger value,
      final Config config, final String name) {
    if (value != null) {
      return value;
    } else {
      return config.getBigInteger(name);
    }
  }

  /**
   * Choose the non-null value of a specified value and a property value in a
   * {@link Config}.
   *
   * @param value
   *     the specified value, which could be {@code null}.
   * @param config
   *     the specified {@link Config}.
   * @param name
   *     the name of the specified property in the {@link Config}.
   * @param defaultValue
   *     the default value to use if the specified {@link Config} has no
   *     property with the specified name or the specified property has no
   *     value.
   * @return if the specified value is not {@code null}, returns the specified
   *     value; otherwise, returns the specified property value in the specified
   *     {@link Config}; if there is no such property or the property has no
   *     value, returns the default value.
   */
  public static BigInteger choose(@Nullable final BigInteger value,
      final Config config, final String name,
      @Nullable final BigInteger defaultValue) {
    if (value != null) {
      return value;
    } else {
      return config.getBigInteger(name, defaultValue);
    }
  }

  /**
   * Choose the non-null value of a specified value and a property value in a
   * {@link Config}.
   *
   * @param value
   *     the specified value, which could be {@code null}.
   * @param config
   *     the specified {@link Config}.
   * @param name
   *     the name of the specified property in the {@link Config}.
   * @return if the specified value is not {@code null}, returns the specified
   *     value; otherwise, returns the specified property value in the specified
   *     {@link Config}.
   * @throws ConfigurationError
   *     if there is no property with the specified name, or the specified
   *     property has no value, or the type of the specified property is not
   *     {@code BigDecimal}.
   */
  public static BigDecimal choose(@Nullable final BigDecimal value,
      final Config config, final String name) {
    if (value != null) {
      return value;
    } else {
      return config.getBigDecimal(name);
    }
  }

  /**
   * Choose the non-null value of a specified value and a property value in a
   * {@link Config}.
   *
   * @param value
   *     the specified value, which could be {@code null}.
   * @param config
   *     the specified {@link Config}.
   * @param name
   *     the name of the specified property in the {@link Config}.
   * @param defaultValue
   *     the default value to use if the specified {@link Config} has no
   *     property with the specified name or the specified property has no
   *     value.
   * @return if the specified value is not {@code null}, returns the specified
   *     value; otherwise, returns the specified property value in the specified
   *     {@link Config}; if there is no such property or the property has no
   *     value, returns the default value.
   */
  public static BigDecimal choose(@Nullable final BigDecimal value,
      final Config config, final String name,
      @Nullable final BigDecimal defaultValue) {
    if (value != null) {
      return value;
    } else {
      return config.getBigDecimal(name, defaultValue);
    }
  }

  /**
   * Choose the non-null value of a specified value and a property value in a
   * {@link Config}.
   *
   * @param value
   *     the specified value, which could be {@code null}.
   * @param config
   *     the specified {@link Config}.
   * @param name
   *     the name of the specified property in the {@link Config}.
   * @return if the specified value is not {@code null}, returns the specified
   *     value; otherwise, returns the specified property value in the specified
   *     {@link Config}.
   * @throws ConfigurationError
   *     if there is no property with the specified name, or the specified
   *     property has no value, or the type of the specified property is not
   *     {@code class}.
   */
  public static Class<?> choose(@Nullable final Class<?> value,
      final Config config, final String name) {
    if (value != null) {
      return value;
    } else {
      return config.getClass(name);
    }
  }

  /**
   * Choose the non-null value of a specified value and a property value in a
   * {@link Config}.
   *
   * @param value
   *     the specified value, which could be {@code null}.
   * @param config
   *     the specified {@link Config}.
   * @param name
   *     the name of the specified property in the {@link Config}.
   * @param defaultValue
   *     the default value to use if the specified {@link Config} has no
   *     property with the specified name or the specified property has no
   *     value.
   * @return if the specified value is not {@code null}, returns the specified
   *     value; otherwise, returns the specified property value in the specified
   *     {@link Config}; if there is no such property or the property has no
   *     value, returns the default value.
   */
  public static Class<?> choose(@Nullable final Class<?> value,
      final Config config, final String name,
      @Nullable final Class<?> defaultValue) {
    if (value != null) {
      return value;
    } else {
      return config.getClass(name, defaultValue);
    }
  }

  /**
   * Choose the non-null value of a specified value and a property value in a
   * {@link Config}.
   *
   * @param <T>
   *     the generic type of the specified value.
   * @param value
   *     the specified value, which could be {@code null}.
   * @param config
   *     the specified {@link Config}.
   * @param name
   *     the name of the specified property in the {@link Config}.
   * @return if the specified value is not {@code null}, returns the specified
   *     value; otherwise, returns the specified property value in the specified
   *     {@link Config}.
   * @throws ConfigurationError
   *     if there is no property with the specified name, or the specified
   *     property has no value, or the type of the specified property is not
   *     {@code class}.
   */
  public static <T> T choose(@Nullable final T value, final Config config,
      final String name) {
    if (value != null) {
      return value;
    } else {
      return config.getInstance(name);
    }
  }

  /**
   * Choose the non-null value of a specified value and a property value in a
   * {@link Config}.
   *
   * @param <T>
   *     the generic type of the specified value.
   * @param value
   *     the specified value, which could be {@code null}.
   * @param config
   *     the specified {@link Config}.
   * @param name
   *     the name of the specified property in the {@link Config}.
   * @param defaultValue
   *     the default value to use if the specified {@link Config} has no
   *     property with the specified name or the specified property has no
   *     value.
   * @return if the specified value is not {@code null}, returns the specified
   *     value; otherwise, returns the specified property value in the specified
   *     {@link Config}; if there is no such property or the property has no
   *     value, returns the default value.
   */
  public static <T> T choose(@Nullable final T value, final Config config,
      final String name, @Nullable final T defaultValue) {
    if (value != null) {
      return value;
    } else {
      return config.getInstance(name, defaultValue);
    }
  }

  /**
   * Choose the non-null value of a specified value and a property value in a
   * {@link Config}.
   *
   * @param <T>
   *     the generic type of the specified value.
   * @param value
   *     the specified value, which could be {@code null}.
   * @param config
   *     the specified {@link Config}.
   * @param name
   *     the name of the specified property in the {@link Config}.
   * @param defaultClass
   *     the default class to use if the specified {@link Config} has no
   *     property with the specified name or the specified property has no
   *     value.
   * @return if the specified value is not {@code null}, returns the specified
   *     value; otherwise, returns the instance created from the specified
   *     property value in the specified {@link Config}; if there is no such
   *     property or the property has no value, returns the instance created
   *     from the default class.
   */
  public static <T> T choose(@Nullable final T value, final Config config,
      final String name, @Nullable final Class<?> defaultClass) {
    if (value != null) {
      return value;
    } else {
      return config.getInstance(name, defaultClass);
    }
  }

  /**
   * Choose the non-null value of a specified value and a property value in a
   * {@link Config}.
   *
   * @param <T>
   *     the generic type of the specified value.
   * @param value
   *     the specified value, which could be {@code null}.
   * @param config
   *     the specified {@link Config}.
   * @param name
   *     the name of the specified property in the {@link Config}.
   * @param defaultClassName
   *     the default class name to use if the specified {@link Config} has no
   *     property with the specified name or the specified property has no
   *     value.
   * @return if the specified value is not {@code null}, returns the specified
   *     value; otherwise, returns the instance created from the specified
   *     property value in the specified {@link Config}; if there is no such
   *     property or the property has no value, returns the instance created
   *     from the default class name.
   */
  public static <T> T choose(@Nullable final T value, final Config config,
      final String name, @Nullable final String defaultClassName) {
    if (value != null) {
      return value;
    } else {
      return config.getInstance(name, defaultClassName);
    }
  }

  /**
   * Loads a configuration from the specified XML resource.
   *
   * <p>The function will search the XML resource in the following order:
   *
   * <ol>
   * <li>First it will search the system's properties (using
   * {@link System#getProperty(String)}) for the specified property name, and if
   * the property was presented, the property value will be used as the XML
   * resource name.</li>
   * <li>If no such system property, the default XML resource name will be used.
   * </li>
   * </ol>
   *
   * <p>Then the function will try to load the configuration form the resource
   * in the context of the specified class. If success, the configuration is
   * returned; otherwise, an empty configuration is returned.
   *
   * <p>For example, suppose there is a system property
   * "com.github.commons.config" whose value is "java-commons.xml",
   * calling this function with the argument of
   * "com.github.commons.config" will gets a configuration load from
   * the resource "java-commons.xml".
   *
   * @param propertyName
   *     the name for the property in the system properties which specifies the
   *     resource name of the configuration. It could be null, indicating the
   *     function should use the default resource directly.
   * @param defaultResource
   *     the name of the default resource for the configuration. It can't be
   *     null.
   * @param clazz
   *     the class under whose context the resource will be searched.
   * @return the configuration load from that resource, or an empty
   *     configuration if failed.
   */
  public static Config loadXmlConfig(@Nullable final String propertyName,
      final String defaultResource, final Class<?> clazz) {
    String resource;
    if (propertyName == null) {
      resource = defaultResource;
    } else {
      resource = SystemUtils.getProperty(propertyName);
      if (resource != null) {
        LOGGER.debug("Use the resource from system property '{}': {}",
            propertyName, resource);
      } else {
        LOGGER.info("No system property '{}', use the default resource: {}",
            propertyName, defaultResource);
        resource = defaultResource;
      }
    }
    try {
      return XmlSerialization.deserialize(DefaultConfig.class, resource, clazz);
    } catch (final XmlException e) {
      // LOGGER.error("Failed to load the configuration from the resource: {}, "
      //     + "use an empty configuration: {}", resource, e.getMessage(), e);
      return new DefaultConfig();
    }
  }

}