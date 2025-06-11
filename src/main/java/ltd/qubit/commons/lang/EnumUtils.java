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
 * 为 {@link Enum} 提供实用工具函数。
 *
 * @author 胡海星
 */
public class EnumUtils {

  /**
   * 获取 {@link Enum} 值的短名称。
   *
   * <p>{@link Enum} 值的短名称来自于将枚举名称转换为小写，并将所有的 '_' 替换为 '-'。
   *
   * @param <E>
   *     {@link Enum} 类的类型。
   * @param e
   *     一个 {@link Enum} 值。
   * @return {@link Enum} 值的短名称。
   */
  public static <E extends Enum<E>> String getShortName(final E e) {
    return e.name().toLowerCase().replace('_', '-');
  }

  /**
   * 根据 {@link Enum} 值的短名称获取其枚举名称。
   *
   * @param shortName
   *     {@link Enum} 值的短名称。{@link Enum} 值的短名称来自于将枚举名称转换为小写，
   *     并将所有的 '_' 替换为 '-'。
   * @return {@link Enum} 值的枚举名称。
   */
  public static String getFullName(final String shortName) {
    return shortName.replace('-', '_').toUpperCase();
  }

  /**
   * 根据名称获取 {@link Enum} 值。
   *
   * @param <E>
   *     {@link Enum} 类的类型。
   * @param name
   *     {@link Enum} 值的名称，可以是枚举名称或短名称。{@link Enum} 值的短名称来自于
   *     将枚举名称转换为小写，并将所有的 '_' 替换为 '-'。
   * @param isShortName
   *     名称是否为短名称。
   * @param ignoreCase
   *     比较字符串时是否忽略大小写。
   * @param enumClass
   *     {@link Enum} 类的类对象。
   * @return 对应于该名称的 {@link Enum} 值，如果没有这样的值则返回 {@code null}。
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
   * 将枚举值转换为其字符串表示形式。
   *
   * @param <E>
   *     {@link Enum} 类的类型。
   * @param value
   *     一个枚举值。
   * @param useShortName
   *     指示是否转换为枚举值的短名称。{@link Enum} 值的短名称来自于将枚举名称转换为
   *     小写，并将所有的 '_' 替换为 '-'。如果此参数为 {@code false}，枚举值的字符串
   *     表示形式就是其名称（不改变大小写）。
   * @return 枚举值的字符串表示形式。
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
   * 获取下一个枚举值。
   *
   * @param e
   *     当前枚举值。
   * @param <E>
   *     枚举类的类型。
   * @return {@code e} 的下一个枚举值，如果 {@code e} 是最后一个，则返回第一个枚举值。
   */
  public static <E extends Enum<E>> E next(final E e) {
    @SuppressWarnings("unchecked") final Class<E> cls = (Class<E>) e.getClass();
    final E[] values = cls.getEnumConstants();
    return values[(e.ordinal() + 1) % values.length];
  }

  /**
   * 默认字符编码。
   */
  private static final String DEFAULT_ENCODING = "UTF-8";

  /**
   * 资源包映射表，将类键映射到资源包的基础名称。
   */
  private static final Map<ClassKey, String>
      RESOURCE_BUNDLE_MAP = new ConcurrentSkipListMap<>();

  /**
   * 类本地化名称映射表，将类键映射到区域设置和名称的映射。
   */
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

  /**
   * 加载指定枚举类在指定区域设置下的本地化名称映射。
   *
   * @param cls
   *     枚举类的类对象。
   * @param bundle
   *     资源包的基础名称。
   * @param locale
   *     指定的区域设置。
   * @return 枚举名称到本地化名称的映射。
   */
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

  /**
   * 可与枚举类型进行比较的类型集合。
   */
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