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

import javax.annotation.Nullable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ltd.qubit.commons.config.error.ConfigurationError;
import ltd.qubit.commons.config.impl.DefaultConfig;
import ltd.qubit.commons.io.serialize.XmlSerialization;
import ltd.qubit.commons.lang.SystemUtils;
import ltd.qubit.commons.text.xml.XmlException;

/**
 * 提供{@link Config}对象的工具函数。
 *
 * @author 胡海星
 */
public final class ConfigUtils {

  private static final Logger LOGGER = LoggerFactory.getLogger(ConfigUtils.class);

  /**
   * 在 {@link Config} 中选择指定值和属性值中的非空值。
   *
   * @param value
   *     指定的值，可以为 {@code null}。
   * @param config
   *     指定的 {@link Config}。
   * @param name
   *     {@link Config} 中指定属性的名称。
   * @return
   *     如果指定的值不为 {@code null}，则返回指定的值；否则，返回指定的
   *     {@link Config} 中指定的属性值。
   * @throws ConfigurationError
   *     如果不存在具有指定名称的属性，或者指定的属性没有值，或者指定属性的类型不是 {@code boolean}。
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
   * 在 {@link Config} 中选择指定值和属性值中的非空值。
   *
   * @param value
   *     指定的值，可以为 {@code null}。
   * @param config
   *     指定的 {@link Config}。
   * @param name
   *     {@link Config} 中指定属性的名称。
   * @param defaultValue
   *     如果指定的 {@link Config} 没有具有指定名称的属性或指定的属性没有值，则使用的默认值。
   * @return
   *     如果指定的值不为 {@code null}，则返回指定的值；否则，返回指定的
   *     {@link Config} 中指定的属性值；如果没有这样的属性或属性没有值，则返回默认值。
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
   * 在 {@link Config} 中选择指定值和属性值中的非空值。
   *
   * @param value
   *     指定的值，可以为 {@code null}。
   * @param config
   *     指定的 {@link Config}。
   * @param name
   *     {@link Config} 中指定属性的名称。
   * @return
   *     如果指定的值不为 {@code null}，则返回指定的值；否则，返回指定的
   *     {@link Config} 中指定的属性值。
   * @throws ConfigurationError
   *     如果不存在具有指定名称的属性，或者指定的属性没有值，或者指定属性的类型不是 {@code char}。
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
   * 在 {@link Config} 中选择指定值和属性值中的非空值。
   *
   * @param value
   *     指定的值，可以为 {@code null}。
   * @param config
   *     指定的 {@link Config}。
   * @param name
   *     {@link Config} 中指定属性的名称。
   * @param defaultValue
   *     如果指定的 {@link Config} 没有具有指定名称的属性或指定的属性没有值，则使用的默认值。
   * @return
   *     如果指定的值不为 {@code null}，则返回指定的值；否则，返回指定的
   *     {@link Config} 中指定的属性值；如果没有这样的属性或属性没有值，则返回默认值。
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
   * 在 {@link Config} 中选择指定值和属性值中的非空值。
   *
   * @param value
   *     指定的值，可以为 {@code null}。
   * @param config
   *     指定的 {@link Config}。
   * @param name
   *     {@link Config} 中指定属性的名称。
   * @return
   *     如果指定的值不为 {@code null}，则返回指定的值；否则，返回指定的
   *     {@link Config} 中指定的属性值。
   * @throws ConfigurationError
   *     如果不存在具有指定名称的属性，或者指定的属性没有值，或者指定属性的类型不是 {@code byte}。
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
   * 在 {@link Config} 中选择指定值和属性值中的非空值。
   *
   * @param value
   *     指定的值，可以为 {@code null}。
   * @param config
   *     指定的 {@link Config}。
   * @param name
   *     {@link Config} 中指定属性的名称。
   * @param defaultValue
   *     如果指定的 {@link Config} 没有具有指定名称的属性或指定的属性没有值，则使用的默认值。
   * @return
   *     如果指定的值不为 {@code null}，则返回指定的值；否则，返回指定的
   *     {@link Config} 中指定的属性值；如果没有这样的属性或属性没有值，则返回默认值。
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
   * 在 {@link Config} 中选择指定值和属性值中的非空值。
   *
   * @param value
   *     指定的值，可以为 {@code null}。
   * @param config
   *     指定的 {@link Config}。
   * @param name
   *     {@link Config} 中指定属性的名称。
   * @return
   *     如果指定的值不为 {@code null}，则返回指定的值；否则，返回指定的
   *     {@link Config} 中指定的属性值。
   * @throws ConfigurationError
   *     如果不存在具有指定名称的属性，或者指定的属性没有值，或者指定属性的类型不是 {@code short}。
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
   * 在 {@link Config} 中选择指定值和属性值中的非空值。
   *
   * @param value
   *     指定的值，可以为 {@code null}。
   * @param config
   *     指定的 {@link Config}。
   * @param name
   *     {@link Config} 中指定属性的名称。
   * @param defaultValue
   *     如果指定的 {@link Config} 没有具有指定名称的属性或指定的属性没有值，则使用的默认值。
   * @return
   *     如果指定的值不为 {@code null}，则返回指定的值；否则，返回指定的
   *     {@link Config} 中指定的属性值；如果没有这样的属性或属性没有值，则返回默认值。
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
   * 在 {@link Config} 中选择指定值和属性值中的非空值。
   *
   * @param value
   *     指定的值，可以为 {@code null}。
   * @param config
   *     指定的 {@link Config}。
   * @param name
   *     {@link Config} 中指定属性的名称。
   * @return
   *     如果指定的值不为 {@code null}，则返回指定的值；否则，返回指定的
   *     {@link Config} 中指定的属性值。
   * @throws ConfigurationError
   *     如果不存在具有指定名称的属性，或者指定的属性没有值，或者指定属性的类型不是 {@code int}。
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
   * 在 {@link Config} 中选择指定值和属性值中的非空值。
   *
   * @param value
   *     指定的值，可以为 {@code null}。
   * @param config
   *     指定的 {@link Config}。
   * @param name
   *     {@link Config} 中指定属性的名称。
   * @param defaultValue
   *     如果指定的 {@link Config} 没有具有指定名称的属性或指定的属性没有值，则使用的默认值。
   * @return
   *     如果指定的值不为 {@code null}，则返回指定的值；否则，返回指定的
   *     {@link Config} 中指定的属性值；如果没有这样的属性或属性没有值，则返回默认值。
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
   * 在 {@link Config} 中选择指定值和属性值中的非空值。
   *
   * @param value
   *     指定的值，可以为 {@code null}。
   * @param config
   *     指定的 {@link Config}。
   * @param name
   *     {@link Config} 中指定属性的名称。
   * @return
   *     如果指定的值不为 {@code null}，则返回指定的值；否则，返回指定的
   *     {@link Config} 中指定的属性值。
   * @throws ConfigurationError
   *     如果不存在具有指定名称的属性，或者指定的属性没有值，或者指定属性的类型不是 {@code long}。
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
   * 在 {@link Config} 中选择指定值和属性值中的非空值。
   *
   * @param value
   *     指定的值，可以为 {@code null}。
   * @param config
   *     指定的 {@link Config}。
   * @param name
   *     {@link Config} 中指定属性的名称。
   * @param defaultValue
   *     如果指定的 {@link Config} 没有具有指定名称的属性或指定的属性没有值，则使用的默认值。
   * @return
   *     如果指定的值不为 {@code null}，则返回指定的值；否则，返回指定的
   *     {@link Config} 中指定的属性值；如果没有这样的属性或属性没有值，则返回默认值。
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
   * 在 {@link Config} 中选择指定值和属性值中的非空值。
   *
   * @param value
   *     指定的值，可以为 {@code null}。
   * @param config
   *     指定的 {@link Config}。
   * @param name
   *     {@link Config} 中指定属性的名称。
   * @return
   *     如果指定的值不为 {@code null}，则返回指定的值；否则，返回指定的
   *     {@link Config} 中指定的属性值。
   * @throws ConfigurationError
   *     如果不存在具有指定名称的属性，或者指定的属性没有值，或者指定属性的类型不是 {@code float}。
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
   * 在 {@link Config} 中选择指定值和属性值中的非空值。
   *
   * @param value
   *     指定的值，可以为 {@code null}。
   * @param config
   *     指定的 {@link Config}。
   * @param name
   *     {@link Config} 中指定属性的名称。
   * @param defaultValue
   *     如果指定的 {@link Config} 没有具有指定名称的属性或指定的属性没有值，则使用的默认值。
   * @return
   *     如果指定的值不为 {@code null}，则返回指定的值；否则，返回指定的
   *     {@link Config} 中指定的属性值；如果没有这样的属性或属性没有值，则返回默认值。
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
   * 在 {@link Config} 中选择指定值和属性值中的非空值。
   *
   * @param value
   *     指定的值，可以为 {@code null}。
   * @param config
   *     指定的 {@link Config}。
   * @param name
   *     {@link Config} 中指定属性的名称。
   * @return
   *     如果指定的值不为 {@code null}，则返回指定的值；否则，返回指定的
   *     {@link Config} 中指定的属性值。
   * @throws ConfigurationError
   *     如果不存在具有指定名称的属性，或者指定的属性没有值，或者指定属性的类型不是 {@code double}。
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
   * 在 {@link Config} 中选择指定值和属性值中的非空值。
   *
   * @param value
   *     指定的值，可以为 {@code null}。
   * @param config
   *     指定的 {@link Config}。
   * @param name
   *     {@link Config} 中指定属性的名称。
   * @param defaultValue
   *     如果指定的 {@link Config} 没有具有指定名称的属性或指定的属性没有值，则使用的默认值。
   * @return
   *     如果指定的值不为 {@code null}，则返回指定的值；否则，返回指定的
   *     {@link Config} 中指定的属性值；如果没有这样的属性或属性没有值，则返回默认值。
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
   * 在 {@link Config} 中选择指定值和属性值中的非空值。
   *
   * @param value
   *     指定的值，可以为 {@code null}。
   * @param config
   *     指定的 {@link Config}。
   * @param name
   *     {@link Config} 中指定属性的名称。
   * @return
   *     如果指定的值不为 {@code null}，则返回指定的值；否则，返回指定的
   *     {@link Config} 中指定的属性值。
   * @throws ConfigurationError
   *     如果不存在具有指定名称的属性，或者指定的属性没有值，或者指定属性的类型不是 {@code String}。
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
   * 在 {@link Config} 中选择指定值和属性值中的非空值。
   *
   * @param value
   *     指定的值，可以为 {@code null}。
   * @param config
   *     指定的 {@link Config}。
   * @param name
   *     {@link Config} 中指定属性的名称。
   * @param defaultValue
   *     如果指定的 {@link Config} 没有具有指定名称的属性或指定的属性没有值，则使用的默认值。
   * @return
   *     如果指定的值不为 {@code null}，则返回指定的值；否则，返回指定的
   *     {@link Config} 中指定的属性值；如果没有这样的属性或属性没有值，则返回默认值。
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
   * 在 {@link Config} 中选择指定值和属性值中的非空值。
   *
   * @param value
   *     指定的值，可以为 {@code null}。
   * @param config
   *     指定的 {@link Config}。
   * @param name
   *     {@link Config} 中指定属性的名称。
   * @return
   *     如果指定的值不为 {@code null}，则返回指定的值；否则，返回指定的
   *     {@link Config} 中指定的属性值。
   * @throws ConfigurationError
   *     如果不存在具有指定名称的属性，或者指定的属性没有值，或者指定属性的类型不是 {@link LocalDate}。
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
   * 在 {@link Config} 中选择指定值和属性值中的非空值。
   *
   * @param value
   *     指定的值，可以为 {@code null}。
   * @param config
   *     指定的 {@link Config}。
   * @param name
   *     {@link Config} 中指定属性的名称。
   * @param defaultValue
   *     如果指定的 {@link Config} 没有具有指定名称的属性或指定的属性没有值，则使用的默认值。
   * @return
   *     如果指定的值不为 {@code null}，则返回指定的值；否则，返回指定的
   *     {@link Config} 中指定的属性值；如果没有这样的属性或属性没有值，则返回默认值。
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
   * 在 {@link Config} 中选择指定值和属性值中的非空值。
   *
   * @param value
   *     指定的值，可以为 {@code null}。
   * @param config
   *     指定的 {@link Config}。
   * @param name
   *     {@link Config} 中指定属性的名称。
   * @return
   *     如果指定的值不为 {@code null}，则返回指定的值；否则，返回指定的
   *     {@link Config} 中指定的属性值。
   * @throws ConfigurationError
   *     如果不存在具有指定名称的属性，或者指定的属性没有值，或者指定属性的类型不是 {@link LocalTime}。
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
   * 在 {@link Config} 中选择指定值和属性值中的非空值。
   *
   * @param value
   *     指定的值，可以为 {@code null}。
   * @param config
   *     指定的 {@link Config}。
   * @param name
   *     {@link Config} 中指定属性的名称。
   * @param defaultValue
   *     如果指定的 {@link Config} 没有具有指定名称的属性或指定的属性没有值，则使用的默认值。
   * @return
   *     如果指定的值不为 {@code null}，则返回指定的值；否则，返回指定的
   *     {@link Config} 中指定的属性值；如果没有这样的属性或属性没有值，则返回默认值。
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
   * 在 {@link Config} 中选择指定值和属性值中的非空值。
   *
   * @param value
   *     指定的值，可以为 {@code null}。
   * @param config
   *     指定的 {@link Config}。
   * @param name
   *     {@link Config} 中指定属性的名称。
   * @return
   *     如果指定的值不为 {@code null}，则返回指定的值；否则，返回指定的
   *     {@link Config} 中指定的属性值。
   * @throws ConfigurationError
   *     如果不存在具有指定名称的属性，或者指定的属性没有值，或者指定属性的类型不是 {@link LocalDateTime}。
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
   * 在 {@link Config} 中选择指定值和属性值中的非空值。
   *
   * @param value
   *     指定的值，可以为 {@code null}。
   * @param config
   *     指定的 {@link Config}。
   * @param name
   *     {@link Config} 中指定属性的名称。
   * @param defaultValue
   *     如果指定的 {@link Config} 没有具有指定名称的属性或指定的属性没有值，则使用的默认值。
   * @return
   *     如果指定的值不为 {@code null}，则返回指定的值；否则，返回指定的
   *     {@link Config} 中指定的属性值；如果没有这样的属性或属性没有值，则返回默认值。
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
   * 在 {@link Config} 中选择指定值和属性值中的非空值。
   *
   * @param value
   *     指定的值，可以为 {@code null}。
   * @param config
   *     指定的 {@link Config}。
   * @param name
   *     {@link Config} 中指定属性的名称。
   * @return
   *     如果指定的值不为 {@code null}，则返回指定的值；否则，返回指定的
   *     {@link Config} 中指定的属性值。
   * @throws ConfigurationError
   *     如果不存在具有指定名称的属性，或者指定的属性没有值，或者指定属性的类型不是 {@link BigInteger}。
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
   * 在 {@link Config} 中选择指定值和属性值中的非空值。
   *
   * @param value
   *     指定的值，可以为 {@code null}。
   * @param config
   *     指定的 {@link Config}。
   * @param name
   *     {@link Config} 中指定属性的名称。
   * @param defaultValue
   *     如果指定的 {@link Config} 没有具有指定名称的属性或指定的属性没有值，则使用的默认值。
   * @return
   *     如果指定的值不为 {@code null}，则返回指定的值；否则，返回指定的
   *     {@link Config} 中指定的属性值；如果没有这样的属性或属性没有值，则返回默认值。
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
   * 在 {@link Config} 中选择指定值和属性值中的非空值。
   *
   * @param value
   *     指定的值，可以为 {@code null}。
   * @param config
   *     指定的 {@link Config}。
   * @param name
   *     {@link Config} 中指定属性的名称。
   * @return
   *     如果指定的值不为 {@code null}，则返回指定的值；否则，返回指定的
   *     {@link Config} 中指定的属性值。
   * @throws ConfigurationError
   *     如果不存在具有指定名称的属性，或者指定的属性没有值，或者指定属性的类型不是 {@link BigDecimal}。
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
   * 在 {@link Config} 中选择指定值和属性值中的非空值。
   *
   * @param value
   *     指定的值，可以为 {@code null}。
   * @param config
   *     指定的 {@link Config}。
   * @param name
   *     {@link Config} 中指定属性的名称。
   * @param defaultValue
   *     如果指定的 {@link Config} 没有具有指定名称的属性或指定的属性没有值，则使用的默认值。
   * @return
   *     如果指定的值不为 {@code null}，则返回指定的值；否则，返回指定的
   *     {@link Config} 中指定的属性值；如果没有这样的属性或属性没有值，则返回默认值。
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
   * 在 {@link Config} 中选择指定值和属性值中的非空值。
   *
   * @param value
   *     指定的值，可以为 {@code null}。
   * @param config
   *     指定的 {@link Config}。
   * @param name
   *     {@link Config} 中指定属性的名称。
   * @return
   *     如果指定的值不为 {@code null}，则返回指定的值；否则，返回指定的
   *     {@link Config} 中指定的属性值。
   * @throws ConfigurationError
   *     如果不存在具有指定名称的属性，或者指定的属性没有值，或者指定属性的类型不是 {@link Class}。
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
   * 在 {@link Config} 中选择指定值和属性值中的非空值。
   *
   * @param value
   *     指定的值，可以为 {@code null}。
   * @param config
   *     指定的 {@link Config}。
   * @param name
   *     {@link Config} 中指定属性的名称。
   * @param defaultValue
   *     如果指定的 {@link Config} 没有具有指定名称的属性或指定的属性没有值，则使用的默认值。
   * @return
   *     如果指定的值不为 {@code null}，则返回指定的值；否则，返回指定的
   *     {@link Config} 中指定的属性值；如果没有这样的属性或属性没有值，则返回默认值。
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
   * 在 {@link Config} 中选择指定值和属性值中的非空值。
   *
   * @param <T>
   *     要返回的值的类型。
   * @param value
   *     指定的值，可以为 {@code null}。
   * @param config
   *     指定的 {@link Config}。
   * @param name
   *     {@link Config} 中指定属性的名称。
   * @return
   *     如果指定的值不为 {@code null}，则返回指定的值；否则，返回指定的
   *     {@link Config} 中指定的属性值。
   * @throws ConfigurationError
   *     如果不存在具有指定名称的属性，或者指定的属性没有值，或者指定属性的类型不是 {@code T}。
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
   * 在 {@link Config} 中选择指定值和属性值中的非空值。
   *
   * @param <T>
   *     要返回的值的类型。
   * @param value
   *     指定的值，可以为 {@code null}。
   * @param config
   *     指定的 {@link Config}。
   * @param name
   *     {@link Config} 中指定属性的名称。
   * @param defaultValue
   *     如果指定的 {@link Config} 没有具有指定名称的属性或指定的属性没有值，则使用的默认值。
   * @return
   *     如果指定的值不为 {@code null}，则返回指定的值；否则，返回指定的
   *     {@link Config} 中指定的属性值；如果没有这样的属性或属性没有值，则返回默认值。
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
   * 在 {@link Config} 中选择指定值和属性值中的非空值。
   *
   * @param <T>
   *     要返回的值的类型。
   * @param value
   *     指定的值，可以为 {@code null}。
   * @param config
   *     指定的 {@link Config}。
   * @param name
   *     {@link Config} 中指定属性的名称。
   * @param defaultClass
   *     如果指定的 {@link Config} 没有具有指定名称的属性或指定的属性没有值，则使用的默认类的类对象。
   * @return
   *     如果指定的值不为 {@code null}，则返回指定的值；否则，返回指定的
   *     {@link Config} 中指定的属性值；如果没有这样的属性或属性没有值，则返回默认类的实例。
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
   * 在 {@link Config} 中选择指定值和属性值中的非空值。
   *
   * @param <T>
   *     要返回的值的类型。
   * @param value
   *     指定的值，可以为 {@code null}。
   * @param config
   *     指定的 {@link Config}。
   * @param name
   *     {@link Config} 中指定属性的名称。
   * @param defaultClassName
   *     如果指定的 {@link Config} 没有具有指定名称的属性或指定的属性没有值，则使用的默认类的名称。
   * @return
   *     如果指定的值不为 {@code null}，则返回指定的值；否则，返回指定的
   *     {@link Config} 中指定的属性值；如果没有这样的属性或属性没有值，则返回默认类的实例。
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
   * 从指定的 XML 资源加载配置。
   *
   * <p>该函数将按如下顺序查找 XML 资源：
   *
   * <ol>
   * <li>首先，它会在系统属性中查找指定的属性名（使用
   * {@link System#getProperty(String)}），如果该属性存在，则其属性值将被用作 XML 资源名。</li>
   * <li>如果没有该系统属性，则使用默认的 XML 资源名。</li>
   * </ol>
   *
   * <p>然后，该函数会尝试在指定类的上下文中从资源加载配置。如果成功，则返回该配置；否则，返回一个空配置。
   *
   * <p>例如，假设存在一个名为 "com.github.commons.config" 的系统属性，其值为 "java-commons.xml"，
   * 调用该函数并传入 "com.github.commons.config" 作为参数时，将会从资源 "java-commons.xml" 加载配置。
   *
   * @param propertyName
   *     系统属性的名称。此系统属性的值是 {@link Config} 的 XML 资源的路径。
   *     如果此参数为 {@code null}，或指定名称的系统属性不存在，则该函数将尝试从默认资源加载。
   * @param defaultResource
   *     默认资源的路径。
   * @param clazz
   *     用于获取类加载器以加载资源的类。
   * @return 从指定的 XML 资源加载的 {@link Config}。
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