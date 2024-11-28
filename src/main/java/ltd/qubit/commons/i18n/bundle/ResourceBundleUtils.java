////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
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
 * Provides helping function for resource bundles.
 *
 * @author Haixing Hu
 */
public final class ResourceBundleUtils {

  private static final Map<String, ResourceBundle> bundleMap = new HashMap<>();

  /**
   * Gets a resource bundle of a specified name.
   *
   * <p>The name may have one of the following formats:
   *
   * <pre><code>
   *   [bundleName].properties
   *   [bundleName]_[language].properties
   *   [bundleName]_[language]_[country].properties
   * </code></pre>
   *
   * <p>where {@code [bundleName]} is the name of the bundle, which can not
   * contains the '_' character; [language] is a two lowercase letter language
   * code, {@code [country]} is a two uppercase letter country code.
   *
   * @param bundleName
   *     the name of a bundle.
   * @return The resource bundle of the specified name.
   * @throws MissingResourceException
   *     If the resource bundle can not be found.
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
   * Gets the string of a specified key from a specified resource bundle.
   *
   * @param bundleName
   *     The name of the resource bundle.
   * @param key
   *     The key of the message to be get.
   * @return The string of a specified key from a specified resource bundle.
   * @throws MissingResourceException
   *     If the resource bundle can not be found, or the message of the
   *     specified key can not be found.
   */
  public static String getString(final String bundleName, final String key)
      throws MissingResourceException {
    final ResourceBundle bundle = getBundle(bundleName);
    return bundle.getString(key);
  }

  /**
   * Gets the message of a specified key from a specified resource bundle,
   * formatted without arguments.
   *
   * @param bundleName
   *     The name of the resource bundle.
   * @param key
   *     The unique identifier of the message
   * @return the formatted message.
   * @throws MissingResourceException
   *     If the resource bundle can not be found, or the message of the
   *     specified key can not be found.
   */
  public static String getMessage(final String bundleName, final String key)
      throws MissingResourceException {
    return getMessage(bundleName, key, new Object[]{});
  }

  /**
   * Gets the message of a specified key from a specified resource bundle,
   * formatted using specified arguments.
   *
   * @param bundleName
   *     The name of the resource bundle.
   * @param key
   *     The unique identifier of the message
   * @param arguments
   *     The arguments used to format the message.
   * @return the formatted message.
   * @throws MissingResourceException
   *     If the resource bundle can not be found, or the message of the
   *     specified key can not be found.
   */
  public static String getMessage(final String bundleName, final String key,
      final Object... arguments) throws MissingResourceException {
    final ResourceBundle bundle = getBundle(bundleName);
    final String msgFormatStr = bundle.getString(key);
    final MessageFormat msgFormat = new MessageFormat(msgFormatStr);
    return msgFormat.format(arguments);
  }
}
