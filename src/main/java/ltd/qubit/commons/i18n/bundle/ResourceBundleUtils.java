////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.i18n.bundle;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import static ltd.qubit.commons.lang.Argument.requireNonNull;

/**
 * 为资源包提供辅助函数。
 *
 * @author 胡海星
 */
public final class ResourceBundleUtils {

  private static final Map<String, ResourceBundle> bundleMap = new HashMap<>();

  /**
   * 获取指定名称的资源包。
   *
   * <p>名称可能具有以下格式之一：
   *
   * <pre><code>
   *   [bundleName].properties
   *   [bundleName]_[language].properties
   *   [bundleName]_[language]_[country].properties
   * </code></pre>
   *
   * <p>其中 {@code [bundleName]} 是包的名称，不能包含 '_' 字符；{@code [language]} 是两个小写字母的语言代码，
   * {@code [country]} 是两个大写字母的国家代码。
   *
   * @param bundleName
   *     包的名称。
   * @return 指定名称的资源包。
   * @throws MissingResourceException
   *     如果找不到资源包。
   */
  public static ResourceBundle getBundle(final String bundleName)
      throws MissingResourceException {
    requireNonNull("bundleName", bundleName);
    synchronized (bundleMap) {
      // try to load bundle from cache
      final ResourceBundle bundle = bundleMap.get(bundleName);
      if (bundle != null) {
        return bundle;
      }
    }
    final int firstUnderscore = bundleName.indexOf('_');
    final int secondUnderscore = bundleName.indexOf('_', firstUnderscore + 1);
    final Locale locale;
    if (firstUnderscore != -1) {
      if (secondUnderscore != -1) {
        final String language = bundleName.substring(firstUnderscore + 1,
            secondUnderscore);
        final String country = bundleName.substring(secondUnderscore + 1);
        locale = new Locale(language, country);
      } else {
        // secondUnderscore == -1
        final String language = bundleName.substring(firstUnderscore + 1);
        locale = new Locale(language);
      }
    } else {
      locale = Locale.getDefault();
    }
    // initialize the bundle
    final ResourceBundle bundle = ResourceBundle.getBundle(bundleName, locale);
    // cache the bundle
    synchronized (bundleMap) {
      bundleMap.put(bundleName, bundle);
    }
    return bundle;
  }

  /**
   * 从指定的资源包中获取指定键的字符串。
   *
   * @param bundleName
   *     资源包的名称。
   * @param key
   *     要获取的消息的键。
   * @return 指定资源包中指定键的字符串。
   * @throws MissingResourceException
   *     如果找不到资源包，或找不到指定键的消息。
   */
  public static String getString(final String bundleName, final String key)
      throws MissingResourceException {
    final ResourceBundle bundle = getBundle(bundleName);
    return bundle.getString(key);
  }

  /**
   * 从指定的资源包中获取指定键的消息，不使用参数进行格式化。
   *
   * @param bundleName
   *     资源包的名称。
   * @param key
   *     消息的唯一标识符。
   * @return 格式化的消息。
   * @throws MissingResourceException
   *     如果找不到资源包，或找不到指定键的消息。
   */
  public static String getMessage(final String bundleName, final String key)
      throws MissingResourceException {
    return getMessage(bundleName, key, new Object[]{});
  }

  /**
   * 从指定的资源包中获取指定键的消息，使用指定的参数进行格式化。
   *
   * @param bundleName
   *     资源包的名称。
   * @param key
   *     消息的唯一标识符。
   * @param arguments
   *     用于格式化消息的参数。
   * @return 格式化的消息。
   * @throws MissingResourceException
   *     如果找不到资源包，或找不到指定键的消息。
   */
  public static String getMessage(final String bundleName, final String key,
      final Object... arguments) throws MissingResourceException {
    final ResourceBundle bundle = getBundle(bundleName);
    final String msgFormatStr = bundle.getString(key);
    final MessageFormat msgFormat = new MessageFormat(msgFormatStr);
    return msgFormat.format(arguments);
  }
}