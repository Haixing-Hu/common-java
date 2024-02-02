////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.i18n.message;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.net.URLConnection;
import java.text.MessageFormat;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.Nullable;

import ltd.qubit.commons.lang.SystemUtils;

/**
 * A {@link MessageSource} implementation that accesses resource bundles using
 * specified basenames.
 * <p>
 * This class relies on the underlying JDK's {@link ResourceBundle}
 * implementation, in combination with the JDK's standard message parsing
 * provided by {@link MessageFormat}.
 * <p>
 * This {@link MessageSource} caches both the accessed {@link ResourceBundle}
 * instances and the generated {@link MessageFormat} for each message. It also
 * implements rendering of no-arg messages without MessageFormat, as supported
 * by the {@link AbstractMessageSource} base class. The caching provided by this
 * {@link MessageSource}is significantly faster than the built-in caching of the
 * {@code ResourceBundle} class.
 * <p>
 * The basenames follow {@link ResourceBundle} conventions: essentially,
 * a fully-qualified classpath location. If it doesn't contain a package
 * qualifier (such as {@code org.mypackage}), it will be resolved from the
 * classpath root. Note that the JDK's standard ResourceBundle treats dots as
 * package separators: This means that "test.theme" is effectively equivalent to
 * "test/theme".
 * <p>
 * On the classpath, bundle resources will be read with the locally configured
 * {@link #setDefaultEncoding encoding}: by default, ISO-8859-1; consider
 * switching this to UTF-8, or to {@code null} for the platform default
 * encoding. On the JDK 9+ module path where locally provided
 * {@code ResourceBundle.Control} handles are not supported, this MessageSource
 * always falls back to {@link ResourceBundle#getBundle} retrieval with the
 * platform default encoding: UTF-8 with a ISO-8859-1 fallback on JDK 9+
 * (configurable through the "java.util.PropertyResourceBundle.encoding" system
 * property). Note that {@link #loadBundle(Reader)}/{@link #loadBundle(InputStream)}
 * won't be called in this case either, effectively ignoring overrides in subclasses.
 * Consider implementing a JDK 9 {@code java.util.spi.ResourceBundleProvider}
 * instead.
 * <p>
 * This class is a copy of
 * {@code org.springframework.context.support.ResourceBundleMessageSource}
 * with slight modifications. It is used to avoid the dependency of Spring Framework.
 *
 * @author Rod Johnson
 * @author Juergen Hoeller
 * @author Qimiao Chen
 * @author Haixing Hu
 * @see #setBasenames
 * @see ReloadableResourceBundleMessageSource
 * @see ResourceBundle
 * @see MessageFormat
 */
public class ResourceBundleMessageSource extends
    AbstractResourceBasedMessageSource {

  @Nullable
  private ClassLoader bundleClassLoader;

  @Nullable
  private ClassLoader beanClassLoader = SystemUtils.getDefaultClassLoader();

  /**
   * Cache to hold loaded ResourceBundles. This Map is keyed with the bundle
   * basename, which holds a Map that is keyed with the Locale and in turn holds
   * the ResourceBundle instances. This allows for very efficient hash lookups,
   * significantly faster than the ResourceBundle class's own cache.
   */
  private final Map<String, Map<Locale, ResourceBundle>>
      cachedResourceBundles = new ConcurrentHashMap<>();

  /**
   * Cache to hold already generated MessageFormats. This Map is keyed with the
   * ResourceBundle, which holds a Map that is keyed with the message code,
   * which in turn holds a Map that is keyed with the Locale and holds the
   * MessageFormat values. This allows for very efficient hash lookups without
   * concatenated keys.
   *
   * @see #getMessageFormat
   */
  private final Map<ResourceBundle, Map<String, Map<Locale, MessageFormat>>>
      cachedBundleMessageFormats = new ConcurrentHashMap<>();

  @Nullable
  private volatile MessageSourceControl control = new MessageSourceControl();

  public ResourceBundleMessageSource() {
    setDefaultEncoding("ISO-8859-1");
  }

  /**
   * Set the ClassLoader to load resource bundles with.
   * <p>
   * Default is the containing BeanFactory's
   * {@link org.springframework.beans.factory.BeanClassLoaderAware bean
   * ClassLoader}, or the default ClassLoader determined by
   * {@link org.springframework.util.ClassUtils#getDefaultClassLoader()} if not
   * running within a BeanFactory.
   */
  public void setBundleClassLoader(@Nullable final ClassLoader classLoader) {
    this.bundleClassLoader = classLoader;
  }

  /**
   * Return the ClassLoader to load resource bundles with.
   * <p>
   * Default is the containing BeanFactory's bean ClassLoader.
   *
   * @see #setBundleClassLoader
   */
  @Nullable
  protected ClassLoader getBundleClassLoader() {
    return (this.bundleClassLoader != null ? this.bundleClassLoader : this.beanClassLoader);
  }

  public void setBeanClassLoader(@Nullable final ClassLoader classLoader) {
    this.beanClassLoader = classLoader;
  }

  /**
   * Resolves the given message code as key in the registered resource bundles,
   * returning the value found in the bundle as-is (without MessageFormat
   * parsing).
   */
  @Override
  protected String resolveCodeWithoutArguments(final String code, final Locale locale) {
    final Set<String> basenames = getBasenameSet();
    for (final String basename : basenames) {
      final ResourceBundle bundle = getResourceBundle(basename, locale);
      if (bundle != null) {
        final String result = getStringOrNull(bundle, code);
        if (result != null) {
          return result;
        }
      }
    }
    return null;
  }

  /**
   * Resolves the given message code as key in the registered resource bundles,
   * using a cached MessageFormat instance per message code.
   */
  @Override
  @Nullable
  protected MessageFormat resolveCode(final String code, final Locale locale) {
    final Set<String> basenames = getBasenameSet();
    for (final String basename : basenames) {
      final ResourceBundle bundle = getResourceBundle(basename, locale);
      if (bundle != null) {
        final MessageFormat messageFormat = getMessageFormat(bundle, code, locale);
        if (messageFormat != null) {
          return messageFormat;
        }
      }
    }
    return null;
  }


  /**
   * Return a ResourceBundle for the given basename and Locale, fetching already
   * generated ResourceBundle from the cache.
   *
   * @param basename
   *     the basename of the ResourceBundle
   * @param locale
   *     the Locale to find the ResourceBundle for
   * @return the resulting ResourceBundle, or {@code null} if none found for the
   *     given basename and Locale
   */
  @Nullable
  protected ResourceBundle getResourceBundle(final String basename, final Locale locale) {
    if (getCacheMillis() >= 0) {
      // Fresh ResourceBundle.getBundle call in order to let ResourceBundle
      // do its native caching, at the expense of more extensive lookup steps.
      return doGetBundle(basename, locale);
    } else {
      // Cache forever: prefer locale cache over repeated getBundle calls.
      Map<Locale, ResourceBundle> localeMap = this.cachedResourceBundles.get(basename);
      if (localeMap != null) {
        final ResourceBundle bundle = localeMap.get(locale);
        if (bundle != null) {
          return bundle;
        }
      }
      try {
        final ResourceBundle bundle = doGetBundle(basename, locale);
        if (localeMap == null) {
          localeMap = this.cachedResourceBundles
              .computeIfAbsent(basename, bn -> new ConcurrentHashMap<>());
        }
        localeMap.put(locale, bundle);
        return bundle;
      } catch (final MissingResourceException e) {
        if (logger.isWarnEnabled()) {
          logger.warn("ResourceBundle [{}] not found for MessageSource: {}",
              basename, e.getMessage());
        }
        // Assume bundle not found
        // -> do NOT throw the exception to allow for checking parent message source.
        return null;
      }
    }
  }

  /**
   * Obtain the resource bundle for the given basename and Locale.
   *
   * @param basename
   *     the basename to look for
   * @param locale
   *     the Locale to look for
   * @return the corresponding ResourceBundle
   * @throws MissingResourceException
   *     if no matching bundle could be found
   * @see ResourceBundle#getBundle(String, Locale, ClassLoader)
   * @see #getBundleClassLoader()
   */
  protected ResourceBundle doGetBundle(final String basename, final Locale locale)
      throws MissingResourceException {
    final ClassLoader classLoader = getBundleClassLoader();
    if (classLoader == null) {
      throw new IllegalStateException("No bundle ClassLoader set");
    }
    final MessageSourceControl ctl = this.control;
    if (ctl != null) {
      try {
        return ResourceBundle.getBundle(basename, locale, classLoader, ctl);
      } catch (final UnsupportedOperationException e) {
        // Probably in a Jigsaw environment on JDK 9+
        this.control = null;
        final String encoding = getDefaultEncoding();
        if (encoding != null) {
          logger.info("ResourceBundleMessageSource is configured to read resources with encoding '{}' "
              + "but ResourceBundle.Control not supported in current system environment: "
              + "{} - falling back to plain ResourceBundle.getBundle retrieval with the "
              + "platform default encoding. Consider setting the 'defaultEncoding' property to 'null' "
              + "for participating in the platform default and therefore avoiding this log message.",
              encoding, e.getMessage());
        }
      }
    }
    // Fallback: plain getBundle lookup without Control handle
    return ResourceBundle.getBundle(basename, locale, classLoader);
  }

  /**
   * Load a property-based resource bundle from the given reader.
   * <p>
   * This will be called in case of a {@link #setDefaultEncoding "defaultEncoding"},
   * including {@link ResourceBundleMessageSource}'s default ISO-8859-1 encoding.
   * Note that this method can only be called with a {@code ResourceBundle.Control}:
   * When running on the JDK 9+ module path where such control handles are not
   * supported, any overrides in custom subclasses will effectively get ignored.
   * <p>
   * The default implementation returns a {@link PropertyResourceBundle}.
   *
   * @param reader
   *     the reader for the target resource
   * @return the fully loaded bundle
   * @throws IOException
   *     in case of I/O failure
   * @see #loadBundle(InputStream)
   * @see PropertyResourceBundle#PropertyResourceBundle(Reader)
   * @since 4.2
   */
  protected ResourceBundle loadBundle(final Reader reader) throws IOException {
    return new PropertyResourceBundle(reader);
  }

  /**
   * Load a property-based resource bundle from the given input stream, picking
   * up the default properties encoding on JDK 9+.
   * <p>
   * This will only be called with {@link #setDefaultEncoding "defaultEncoding"}
   * set to {@code null}, explicitly enforcing the platform default encoding
   * (which is UTF-8 with a ISO-8859-1 fallback on JDK 9+ but configurable
   * through the "java.util.PropertyResourceBundle.encoding" system property).
   * Note that this method can only be called with a
   * {@code ResourceBundle.Control}: When running on the JDK 9+ module path
   * where such control handles are not supported, any overrides in custom
   * subclasses will effectively get ignored.
   * <p>
   * The default implementation returns a {@link PropertyResourceBundle}.
   *
   * @param inputStream
   *     the input stream for the target resource
   * @return the fully loaded bundle
   * @throws IOException
   *     in case of I/O failure
   * @see #loadBundle(Reader)
   * @see PropertyResourceBundle#PropertyResourceBundle(InputStream)
   * @since 5.1
   */
  protected ResourceBundle loadBundle(final InputStream inputStream)
      throws IOException {
    return new PropertyResourceBundle(inputStream);
  }

  /**
   * Return a MessageFormat for the given bundle and code, fetching already
   * generated MessageFormats from the cache.
   *
   * @param bundle
   *     the ResourceBundle to work on
   * @param code
   *     the message code to retrieve
   * @param locale
   *     the Locale to use to build the MessageFormat
   * @return the resulting MessageFormat, or {@code null} if no message defined
   *     for the given code
   * @throws MissingResourceException
   *     if thrown by the ResourceBundle
   */
  @Nullable
  protected MessageFormat getMessageFormat(final ResourceBundle bundle,
      final String code, final Locale locale) throws MissingResourceException {
    Map<String, Map<Locale, MessageFormat>> codeMap =
        this.cachedBundleMessageFormats.get(bundle);
    Map<Locale, MessageFormat> localeMap = null;
    if (codeMap != null) {
      localeMap = codeMap.get(code);
      if (localeMap != null) {
        final MessageFormat result = localeMap.get(locale);
        if (result != null) {
          return result;
        }
      }
    }
    final String msg = getStringOrNull(bundle, code);
    if (msg != null) {
      if (codeMap == null) {
        codeMap = this.cachedBundleMessageFormats
            .computeIfAbsent(bundle, b -> new ConcurrentHashMap<>());
      }
      if (localeMap == null) {
        localeMap = codeMap.computeIfAbsent(code, c -> new ConcurrentHashMap<>());
      }
      final MessageFormat result = createMessageFormat(msg, locale);
      localeMap.put(locale, result);
      return result;
    }

    return null;
  }

  /**
   * Efficiently retrieve the String value for the specified key, or return
   * {@code null} if not found.
   * <p>
   * As of 4.2, the default implementation checks {@code containsKey}
   * before it attempts to call {@code getString} (which would require catching
   * {@code MissingResourceException} for key not found).
   * <p>Can be overridden in subclasses.
   *
   * @param bundle
   *     the ResourceBundle to perform the lookup in
   * @param key
   *     the key to look up
   * @return the associated value, or {@code null} if none
   * @see ResourceBundle#getString(String)
   * @see ResourceBundle#containsKey(String)
   * @since 4.2
   */
  @Nullable
  protected String getStringOrNull(final ResourceBundle bundle, final String key) {
    if (bundle.containsKey(key)) {
      try {
        return bundle.getString(key);
      } catch (final MissingResourceException ex) {
        // Assume key not found for some other reason
        // -> do NOT throw the exception to allow for checking parent message source.
      }
    }
    return null;
  }

  /**
   * Show the configuration of this MessageSource.
   */
  @Override
  public String toString() {
    return getClass().getName() + ": basenames=" + getBasenameSet();
  }


  /**
   * Custom implementation of {@code ResourceBundle.Control}, adding support for
   * custom file encodings, deactivating the fallback to the system locale and
   * activating ResourceBundle's native cache, if desired.
   */
  private class MessageSourceControl extends ResourceBundle.Control {
    @Override
    @Nullable
    public ResourceBundle newBundle(final String baseName, final Locale locale,
        final String format, final ClassLoader loader, final boolean reload)
        throws IllegalAccessException, InstantiationException, IOException {
      // Special handling of default encoding
      if (format.equals("java.properties")) {
        final String bundleName = toBundleName(baseName, locale);
        final String resourceName = toResourceName(bundleName, "properties");
        InputStream inputStream = null;
        if (reload) {
          final URL url = loader.getResource(resourceName);
          if (url != null) {
            final URLConnection connection = url.openConnection();
            if (connection != null) {
              connection.setUseCaches(false);
              inputStream = connection.getInputStream();
            }
          }
        } else {
          inputStream = loader.getResourceAsStream(resourceName);
        }
        if (inputStream != null) {
          final String encoding = getDefaultEncoding();
          if (encoding != null) {
            try (final InputStreamReader bundleReader = new InputStreamReader(
                inputStream, encoding)) {
              return loadBundle(bundleReader);
            }
          } else {
            try (final InputStream bundleStream = inputStream) {
              return loadBundle(bundleStream);
            }
          }
        } else {
          return null;
        }
      } else {
        // Delegate handling of "java.class" format to standard Control
        return super.newBundle(baseName, locale, format, loader, reload);
      }
    }

    @Override
    @Nullable
    public Locale getFallbackLocale(final String baseName, final Locale locale) {
      final Locale defaultLocale = getDefaultLocale();
      if (defaultLocale != null && !defaultLocale.equals(locale)) {
        return defaultLocale;
      } else {
        return null;
      }
    }

    @Override
    public long getTimeToLive(final String baseName, final Locale locale) {
      final long cacheMillis = getCacheMillis();
      return (cacheMillis >= 0 ? cacheMillis : super.getTimeToLive(baseName, locale));
    }

    @Override
    public boolean needsReload(final String baseName, final Locale locale,
        final String format, final ClassLoader loader,
        final ResourceBundle bundle, final long loadTime) {
      if (super.needsReload(baseName, locale, format, loader, bundle, loadTime)) {
        cachedBundleMessageFormats.remove(bundle);
        return true;
      } else {
        return false;
      }
    }
  }
}
