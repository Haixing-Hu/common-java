////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.lang;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListMap;

import javax.annotation.Nullable;

import com.google.common.collect.ImmutableSet;

import ltd.qubit.commons.error.NoSuchMessageException;
import ltd.qubit.commons.i18n.message.ResourceBundleMessageSource;

/**
 * Provides utilities functions for {@link Enum}.
 *
 * @author Haixing Hu
 */
public class EnumUtils {

  /**
   * Gets the short name of an {@link Enum} value.
   *
   * <p>A short name of an {@link Enum} value comes from lowercase the
   * enumeration name of the {@link Enum} value and replacing all '_' with '-'.
   *
   * @param <E>
   *     The type of a {@link Enum} class.
   * @param e
   *     A {@link Enum} value.
   * @return The short name of the {@link Enum} value.
   */
  public static <E extends Enum<E>> String getShortName(final E e) {
    return e.name().toLowerCase().replace('_', '-');
  }

  /**
   * Gets the enumeration name of a short name of an {@link Enum} value.
   *
   * @param shortName
   *     The short name of an {@link Enum} value. A short name of an {@link
   *     Enum} value comes from lowercase the enumeration name of the {@link
   *     Enum} value and replacing all '_' with '-'.
   * @return The enumeration name of the {@link Enum} value.
   */
  public static String getFullName(final String shortName) {
    return shortName.replace('-', '_').toUpperCase();
  }

  /**
   * Gets the {@link Enum} value according to its name.
   *
   * @param <E>
   *     The type of a {@link Enum} class.
   * @param name
   *     The name of an {@link Enum} value, either the enumeration name or the
   *     short name. A short name of an {@link Enum} value comes from lowercase
   *     the enumeration name of the {@link Enum} value and replacing all '_'
   *     with '-'.
   * @param isShortName
   *     whether the name is the short name.
   * @param ignoreCase
   *     whether to ignore the case while comparing strings.
   * @param enumClass
   *     The class object of the {@link Enum} class.
   * @return the {@link Enum} value corresponds to the name, or {@code null} if
   *     no such value.
   */
  public static <E extends Enum<E>> E forName(final String name,
      final boolean isShortName, final boolean ignoreCase,
      final Class<E> enumClass) {
    final String enumName = (isShortName ? getFullName(name) : name);
    final E[] enumValues = enumClass.getEnumConstants();
    if (ignoreCase) {
      for (final E enumValue : enumValues) {
        if (StringUtils.equalsIgnoreCase(enumName, enumValue.name())) {
          return enumValue;
        }
      }
    } else {
      for (final E enumValue : enumValues) {
        if (StringUtils.equals(enumName, enumValue.name())) {
          return enumValue;
        }
      }
    }
    return null;
  }

  /**
   * Converts an enumerator to its string representation.
   *
   * @param <E>
   *     The type of a {@link Enum} class.
   * @param value
   *     an enumerator.
   * @param useShortName
   *     indicates whether to convert to short name of the enumerator. A short
   *     name of an {@link Enum} value comes from lowercase the enumeration name
   *     of the {@link Enum} value and replacing all '_' with '-'. If this
   *     argument is {@code false}, the string representation of an enumerator
   *     is its name (without case changing).
   * @return the string representation of the enumerator.
   */
  public static <E extends Enum<E>> String toString(@Nullable final E value,
      final boolean useShortName) {
    if (value == null) {
      return null;
    } else if (useShortName) {
      return getShortName(value);
    } else {
      return value.name();
    }
  }

  /**
   * Gets the next enumerator.
   *
   * @param e
   *     the current enumerator.
   * @param <E>
   *     the type of the enumeration class.
   * @return the enumerator next to {@code e}, or the first enumerator if {@code
   *     e} is the lase one.
   */
  public static <E extends Enum<E>> E next(final E e) {
    @SuppressWarnings("unchecked") final Class<E> cls = (Class<E>) e.getClass();
    final E[] values = cls.getEnumConstants();
    return values[(e.ordinal() + 1) % values.length];
  }

  private static final String DEFAULT_ENCODING = "UTF-8";

  private static final Map<ClassKey, String>
      RESOURCE_BUNDLE_MAP = new ConcurrentSkipListMap<>();

  private static final Map<ClassKey, Map<Locale, Map<String, String>>>
      CLASS_LOCALIZED_NAME_MAP = new ConcurrentSkipListMap<>();

  /**
   * 注册枚举类型本地化的名称。
   *
   * @param cls
   *     枚举类型的类对象。
   * @param basename
   *     存储本地化名称的 Resource Bundle 的 basename。
   * @param <E>
   *     枚举类型。
   */
  public static <E extends Enum<E>>
  void registerLocalizedNames(final Class<E> cls, final String basename) {
    // 检查 basename 是否是完整的 class name
    if (basename.contains("/")) {
      throw new IllegalArgumentException("The basename of the resource bundle "
          + "must be a fully qualified class name. So it has to be written "
          + "separated with dots instead of '/': " + basename);
    }
    final ClassKey key = new ClassKey(cls);
    RESOURCE_BUNDLE_MAP.put(key, basename);
    CLASS_LOCALIZED_NAME_MAP.put(key, new ConcurrentHashMap<>());
  }

  /**
   * 获取枚举对象的本地化名称。
   *
   * <p><b>注意：</b>该枚举对象的类中必须在<b>静态初始化</b>中调用
   * {@link #registerLocalizedNames}以注册本地化名称。
   *
   * @param locale
   *     指定的区域
   * @param e
   *     指定的枚举对象
   * @return 指定的枚举对象在指定区域下的本地化名称。
   */
  public static String getLocalizedName(final Locale locale, final Enum<?> e) {
    @SuppressWarnings("unchecked")
    final Class<? extends Enum<?>> cls = (Class<? extends Enum<?>>) e.getClass();
    final ClassKey key = new ClassKey(cls);
    final Map<Locale, Map<String, String>> nameMap = CLASS_LOCALIZED_NAME_MAP.get(key);
    if (nameMap == null) {
      throw new RuntimeException("Localization names of the enumeration class "
        + "was not correctly registered.");
    }
    final Map<String, String> map = nameMap.computeIfAbsent(locale, (loc) -> {
      final String bundle = RESOURCE_BUNDLE_MAP.get(key);
      return loadLocalizedNameMap(cls, bundle, loc);
    });
    return map.get(e.name());
  }

  private static Map<String, String>
  loadLocalizedNameMap(final Class<? extends Enum<?>> cls, final String bundle, final Locale locale) {
    final ResourceBundleMessageSource source = new ResourceBundleMessageSource();
    source.setDefaultEncoding(DEFAULT_ENCODING);
    source.setBasename(bundle);
    source.setFallbackToSystemLocale(false);
    final Map<String, String> result = new HashMap<>();
    for (final Enum<?> e : cls.getEnumConstants()) {
      String localizedName;
      try {
        localizedName = source.getMessage(e.name(), null, locale);
      } catch (final NoSuchMessageException exception) {
        localizedName = e.name();
      }
      result.put(e.name(), localizedName);
    }
    return result;
  }

  /**
   * 根据枚举对象的本地化名称，获取枚举对象。
   *
   * @param <E>
   *     枚举类型。
   * @param enumClass
   *     指定的枚举类型的类对象。
   * @param locale
   *     指定的本地化区域。
   * @param localizedName
   *     指定的本地化名称。
   * @return
   *     拥有指定本地化名称的枚举对象，或者{@code null}如果不存在这样的枚举对象。
   */
  public static <E extends Enum<E>>
  E forLocalizedName(final Class<E> enumClass, final Locale locale, final String localizedName) {
    final E[] values = enumClass.getEnumConstants();
    for (final E value : values) {
      if (getLocalizedName(locale, value).equals(localizedName)) {
        return value;
      }
    }
    return null;
  }

  /**
   * 测试指定的类型的值是否可以和{@code Enum}类型的值进行比较。
   *
   * @param type
   *     指定的类型。
   * @return
   *     如果指定的类型的值可以和{@code Enum}类型的值进行比较，返回{@code true}；否则返
   *     回{@code false}。
   */
  public static boolean isComparable(final Class<?> type) {
    return COMPARABLE_TYPES.contains(type) || Enum.class.isAssignableFrom(type);
  }

  private static final Set<Class<?>> COMPARABLE_TYPES =
      ImmutableSet.of(char.class, Character.class, String.class);

  /**
   * 获取指定枚举名称对应的枚举值。
   *
   * <p>此函数类似{@link Enum#valueOf(Class, String)}，但区别在于：</p>
   * <ul>
   * <li>此函数的第一个参数，枚举类的类对象，不需要严格的泛型绑定，这在某些情况下很有用，比如
   * 当这个类对象是根据其名称通过{@link Class#forName(String)}生成的情况；</li>
   * <li>此函数找不到指定的枚举值时，返回一个{@code null}而非抛出异常，这提高了效率且减少了
   * 繁冗的{@code try ... catch ...} 代码。</li>
   * </ul>
   *
   * @param type
   *     枚举类的类对象。
   * @param name
   *     指定的枚举值名称。
   * @return
   *     该名称对应的枚举值，或{@code null}若不存在这样的枚举值。
   * @see #getEnumClass(String)
   */
  public static Enum<?> valueOf(final Class<? extends Enum<?>> type, final String name) {
    final Enum<?>[] values = type.getEnumConstants();
    for (final Enum<?> value : values) {
      if (value.name().equals(name)) {
        return value;
      }
    }
    return null;
  }

  /**
   * 根据类名获取指定的枚举类的类对象。
   *
   * @param className
   *     指定的枚举类的完整名称。
   * @return
   *     该名称所对应的枚举类对象，或{@code null}若找不到此类对象或找到的类对象不是枚举类。
   */
  @SuppressWarnings("unchecked")
  public static Class<? extends Enum<?>> getEnumClass(final String className) {
    try {
      final Class<?> cls = ClassUtils.getClass(className);
      if (!cls.isEnum()) {
        return null;
      }
      return (Class<? extends Enum<?>>) cls;
    } catch (final Exception e) {
      return null;
    }
  }
}