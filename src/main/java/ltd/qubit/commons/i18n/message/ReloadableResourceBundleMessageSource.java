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
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.locks.ReentrantLock;

import javax.annotation.Nullable;

import ltd.qubit.commons.io.io.resource.DefaultResourceLoader;
import ltd.qubit.commons.io.io.resource.Resource;
import ltd.qubit.commons.io.io.resource.ResourceLoader;
import ltd.qubit.commons.io.io.resource.ResourceLoaderAware;
import ltd.qubit.commons.util.properties.DefaultPropertiesPersister;
import ltd.qubit.commons.util.properties.PropertiesPersister;

import static ltd.qubit.commons.lang.ArrayUtils.EMPTY_STRING_ARRAY;

/**
 * Spring-specific {@link MessageSource} implementation that accesses resource
 * bundles using specified basenames, participating in the Spring
 * {@link org.springframework.context.ApplicationContext}'s resource loading.
 * <p>
 * In contrast to the JDK-based {@link ResourceBundleMessageSource}, this class
 * uses {@link java.util.Properties} instances as its custom data structure for
 * messages, loading them via a
 * {@link org.springframework.util.PropertiesPersister} strategy from Spring
 * {@link Resource} handles. This strategy is not only capable of reloading
 * files based on timestamp changes, but also of loading properties files with a
 * specific character encoding. It will detect XML property files as well.
 * <p>
 * Note that the basenames set as {@link #setBasenames "basenames"} property are
 * treated in a slightly different fashion than the "basenames" property of
 * {@link ResourceBundleMessageSource}. It follows the basic ResourceBundle rule
 * of not specifying file extension or language codes, but can refer to any
 * Spring resource location (instead of being restricted to classpath
 * resources). With a "classpath:" prefix, resources can still be loaded from
 * the classpath, but "cacheSeconds" values other than "-1" (caching forever)
 * might not work reliably in this case.
 * <p>
 * For a typical web application, message files could be placed in
 * {@code WEB-INF}: e.g. a "WEB-INF/messages" basename would find a
 * "WEB-INF/messages.properties", "WEB-INF/messages_en.properties" etc
 * arrangement as well as "WEB-INF/messages.xml", "WEB-INF/messages_en.xml" etc.
 * Note that message definitions in a <i>previous</i> resource bundle will
 * override ones in a later bundle, due to sequential lookup.
 * <p>
 * This MessageSource can easily be used outside an
 * {@link org.springframework.context.ApplicationContext}: it will use a
 * {@link DefaultResourceLoader} as default, simply getting overridden with the
 * ApplicationContext's resource loader if running in a context. It does not
 * have any other specific dependencies.
 * <p>
 * Thanks to Thomas Achleitner for providing the initial implementation of this
 * message source!
 * <p>
 * This class is a copy of
 * {@code
 * org.springframework.context.support.ReloadableResourceBundleMessageSource}
 * with slight modifications. It is used to avoid the dependency of Spring
 * Framework.
 *
 * @author Juergen Hoeller
 * @author Sebastien Deleuze
 * @author Haixing Hu
 * @see #setCacheSeconds
 * @see #setBasenames
 * @see #setDefaultEncoding
 * @see #setFileEncodings
 * @see #setPropertiesPersister
 * @see #setResourceLoader
 * @see DefaultResourceLoader
 * @see ResourceBundleMessageSource
 * @see java.util.ResourceBundle
 */
public class ReloadableResourceBundleMessageSource
    extends AbstractResourceBasedMessageSource implements ResourceLoaderAware {

  private static final String XML_EXTENSION = ".xml";

  private List<String> fileExtensions = List.of(".properties", XML_EXTENSION);

  @Nullable
  private Properties fileEncodings;

  private boolean concurrentRefresh = true;

  private PropertiesPersister propertiesPersister = DefaultPropertiesPersister.INSTANCE;

  private ResourceLoader resourceLoader = new DefaultResourceLoader();

  // Cache to hold filename lists per Locale
  private final ConcurrentMap<String, Map<Locale, List<String>>>
      cachedFilenames = new ConcurrentHashMap<>();

  // Cache to hold already loaded properties per filename
  private final ConcurrentMap<String, PropertiesHolder>
      cachedProperties = new ConcurrentHashMap<>();

  // Cache to hold already loaded properties per filename
  private final ConcurrentMap<Locale, PropertiesHolder>
      cachedMergedProperties = new ConcurrentHashMap<>();

  /**
   * Set the list of supported file extensions.
   * <p>
   * The default is a list containing {@code .properties} and {@code .xml}.
   *
   * @param fileExtensions
   *     the file extensions (starts with a dot).
   */
  public void setFileExtensions(final List<String> fileExtensions) {
    if (fileExtensions == null || fileExtensions.isEmpty()) {
      throw new IllegalArgumentException("At least one file extension is required");
    }
    for (final String extension : fileExtensions) {
      if (!extension.startsWith(".")) {
        throw new IllegalArgumentException("File extension '" + extension
            + "' should start with '.'");
      }
    }
    this.fileExtensions = Collections.unmodifiableList(fileExtensions);
  }

  /**
   * Set per-file charsets to use for parsing properties files.
   * <p>
   * Only applies to classic properties files, not to XML files.
   *
   * @param fileEncodings
   *     a Properties with filenames as keys and charset names as values.
   *     Filenames have to match the basename syntax, with optional
   *     locale-specific components: e.g. "WEB-INF/messages" or
   *     "WEB-INF/messages_en".
   * @see #setBasenames
   * @see PropertiesPersister#load
   */
  public void setFileEncodings(@Nullable final Properties fileEncodings) {
    this.fileEncodings = fileEncodings;
  }

  /**
   * Specify whether to allow for concurrent refresh behavior, i.e. one thread
   * locked in a refresh attempt for a specific cached properties file whereas
   * other threads keep returning the old properties for the time being, until
   * the refresh attempt has completed.
   * <p>
   * Default is "true": this behavior is new as of Spring Framework 4.1,
   * minimizing contention between threads. If you prefer the old behavior, i.e.
   * to fully block on refresh, switch this flag to "false".
   *
   * @see #setCacheSeconds
   */
  public void setConcurrentRefresh(final boolean concurrentRefresh) {
    this.concurrentRefresh = concurrentRefresh;
  }

  /**
   * Set the PropertiesPersister to use for parsing properties files.
   * <p>
   * The default is {@code DefaultPropertiesPersister}.
   *
   * @see DefaultPropertiesPersister#INSTANCE
   */
  public void setPropertiesPersister(@Nullable final PropertiesPersister propertiesPersister) {
    this.propertiesPersister = (propertiesPersister != null ?
                                propertiesPersister :
                                DefaultPropertiesPersister.INSTANCE);
  }

  /**
   * Set the ResourceLoader to use for loading bundle properties files.
   * <p>The default is a DefaultResourceLoader. Will get overridden by the
   * ApplicationContext if running in a context, as it implements the
   * ResourceLoaderAware interface. Can be manually overridden when running
   * outside an ApplicationContext.
   *
   * @see DefaultResourceLoader
   * @see ResourceLoaderAware
   */
  @Override
  public void setResourceLoader(@Nullable final ResourceLoader resourceLoader) {
    this.resourceLoader = (resourceLoader != null ? resourceLoader :
                           new DefaultResourceLoader());
  }

  /**
   * Resolves the given message code as key in the retrieved bundle files,
   * returning the value found in the bundle as-is (without MessageFormat
   * parsing).
   */
  @Override
  protected String resolveCodeWithoutArguments(final String code, final Locale locale) {
    if (getCacheMillis() < 0) {
      final PropertiesHolder propHolder = getMergedProperties(locale);
      final String result = propHolder.getProperty(code);
      if (result != null) {
        return result;
      }
    } else {
      for (final String basename : getBasenameSet()) {
        final List<String> filenames = calculateAllFilenames(basename, locale);
        for (final String filename : filenames) {
          final PropertiesHolder propHolder = getProperties(filename);
          final String result = propHolder.getProperty(code);
          if (result != null) {
            return result;
          }
        }
      }
    }
    return null;
  }

  /**
   * Resolves the given message code as key in the retrieved bundle files, using
   * a cached MessageFormat instance per message code.
   */
  @Override
  @Nullable
  protected MessageFormat resolveCode(final String code, final Locale locale) {
    if (getCacheMillis() < 0) {
      final PropertiesHolder propHolder = getMergedProperties(locale);
      final MessageFormat result = propHolder.getMessageFormat(code, locale);
      if (result != null) {
        return result;
      }
    } else {
      for (final String basename : getBasenameSet()) {
        final List<String> filenames = calculateAllFilenames(basename, locale);
        for (final String filename : filenames) {
          final PropertiesHolder propHolder = getProperties(filename);
          final MessageFormat result = propHolder.getMessageFormat(code, locale);
          if (result != null) {
            return result;
          }
        }
      }
    }
    return null;
  }


  /**
   * Get a PropertiesHolder that contains the actually visible properties for a
   * Locale, after merging all specified resource bundles. Either fetches the
   * holder from the cache or freshly loads it.
   * <p>Only used when caching resource bundle contents forever, i.e.
   * with cacheSeconds &lt; 0. Therefore, merged properties are always cached
   * forever.
   */
  protected PropertiesHolder getMergedProperties(final Locale locale) {
    PropertiesHolder mergedHolder = this.cachedMergedProperties.get(locale);
    if (mergedHolder != null) {
      return mergedHolder;
    }
    final Properties mergedProps = newProperties();
    long latestTimestamp = -1;
    final Set<String> basenameSet = getBasenameSet();
    final String[] basenames = (basenameSet == null
                                ? EMPTY_STRING_ARRAY
                                : basenameSet.toArray(new String[0]));
    for (int i = basenames.length - 1; i >= 0; i--) {
      final List<String> filenames = calculateAllFilenames(basenames[i], locale);
      for (int j = filenames.size() - 1; j >= 0; j--) {
        final String filename = filenames.get(j);
        final PropertiesHolder propHolder = getProperties(filename);
        if (propHolder.getProperties() != null) {
          mergedProps.putAll(propHolder.getProperties());
          if (propHolder.getFileTimestamp() > latestTimestamp) {
            latestTimestamp = propHolder.getFileTimestamp();
          }
        }
      }
    }
    mergedHolder = new PropertiesHolder(mergedProps, latestTimestamp);
    final PropertiesHolder existing = this.cachedMergedProperties
        .putIfAbsent(locale, mergedHolder);
    if (existing != null) {
      mergedHolder = existing;
    }
    return mergedHolder;
  }

  /**
   * Calculate all filenames for the given bundle basename and Locale. Will
   * calculate filenames for the given Locale, the system Locale (if
   * applicable), and the default file.
   *
   * @param basename
   *     the basename of the bundle
   * @param locale
   *     the locale
   * @return the List of filenames to check
   * @see #setFallbackToSystemLocale
   * @see #calculateFilenamesForLocale
   */
  protected List<String> calculateAllFilenames(final String basename, final Locale locale) {
    Map<Locale, List<String>> localeMap = this.cachedFilenames.get(basename);
    if (localeMap != null) {
      final List<String> filenames = localeMap.get(locale);
      if (filenames != null) {
        return filenames;
      }
    }
    // Filenames for given Locale
    final List<String> filenames = new ArrayList<>(7);
    filenames.addAll(calculateFilenamesForLocale(basename, locale));
    // Filenames for default Locale, if any
    final Locale defaultLocale = getDefaultLocale();
    if (defaultLocale != null && !defaultLocale.equals(locale)) {
      final List<String> fallbackFilenames = calculateFilenamesForLocale(basename,
          defaultLocale);
      for (final String fallbackFilename : fallbackFilenames) {
        if (!filenames.contains(fallbackFilename)) {
          // Entry for fallback locale that isn't already in filenames list.
          filenames.add(fallbackFilename);
        }
      }
    }
    // Filename for default bundle file
    filenames.add(basename);
    if (localeMap == null) {
      localeMap = new ConcurrentHashMap<>();
      final Map<Locale, List<String>> existing = this.cachedFilenames
          .putIfAbsent(basename, localeMap);
      if (existing != null) {
        localeMap = existing;
      }
    }
    localeMap.put(locale, filenames);
    return filenames;
  }

  /**
   * Calculate the filenames for the given bundle basename and Locale, appending
   * language code, country code, and variant code.
   * <p>
   * For example, basename "messages", Locale "de_AT_oo" &rarr;
   * "messages_de_AT_OO",
   * "messages_de_AT", "messages_de".
   * <p>
   * Follows the rules defined by {@link java.util.Locale#toString()}.
   *
   * @param basename
   *     the basename of the bundle
   * @param locale
   *     the locale
   * @return the List of filenames to check
   */
  protected List<String> calculateFilenamesForLocale(final String basename,
      final Locale locale) {
    final List<String> result = new ArrayList<>(3);
    final String language = locale.getLanguage();
    final String country = locale.getCountry();
    final String variant = locale.getVariant();
    final StringBuilder temp = new StringBuilder(basename);
    temp.append('_');
    if (language.length() > 0) {
      temp.append(language);
      result.add(0, temp.toString());
    }
    temp.append('_');
    if (country.length() > 0) {
      temp.append(country);
      result.add(0, temp.toString());
    }
    if (variant.length() > 0 && (language.length() > 0
        || country.length() > 0)) {
      temp.append('_').append(variant);
      result.add(0, temp.toString());
    }
    return result;
  }

  /**
   * Get a PropertiesHolder for the given filename, either from the cache or
   * freshly loaded.
   *
   * @param filename
   *     the bundle filename (basename + Locale)
   * @return the current PropertiesHolder for the bundle
   */
  protected PropertiesHolder getProperties(final String filename) {
    PropertiesHolder propHolder = this.cachedProperties.get(filename);
    long originalTimestamp = -2;
    if (propHolder != null) {
      originalTimestamp = propHolder.getRefreshTimestamp();
      if (originalTimestamp == -1
          || originalTimestamp
          > System.currentTimeMillis() - getCacheMillis()) {
        // Up to date
        return propHolder;
      }
    } else {
      propHolder = new PropertiesHolder();
      final PropertiesHolder existingHolder = this.cachedProperties
          .putIfAbsent(filename, propHolder);
      if (existingHolder != null) {
        propHolder = existingHolder;
      }
    }
    // At this point, we need to refresh...
    if (this.concurrentRefresh && propHolder.getRefreshTimestamp() >= 0) {
      // A populated but stale holder -> could keep using it.
      if (!propHolder.refreshLock.tryLock()) {
        // Getting refreshed by another thread already ->
        // let's return the existing properties for the time being.
        return propHolder;
      }
    } else {
      propHolder.refreshLock.lock();
    }
    try {
      final PropertiesHolder existingHolder = this.cachedProperties.get(filename);
      if (existingHolder != null
          && existingHolder.getRefreshTimestamp() > originalTimestamp) {
        return existingHolder;
      }
      return refreshProperties(filename, propHolder);
    } finally {
      propHolder.refreshLock.unlock();
    }
  }

  /**
   * Refresh the PropertiesHolder for the given bundle filename.
   * <p>The holder can be {@code null} if not cached before, or a timed-out
   * cache entry
   * (potentially getting re-validated against the current last-modified
   * timestamp).
   *
   * @param filename
   *     the bundle filename (basename + Locale)
   * @param propHolder
   *     the current PropertiesHolder for the bundle
   * @see #resolveResource(String)
   */
  protected PropertiesHolder refreshProperties(final String filename,
      @Nullable PropertiesHolder propHolder) {
    final long refreshTimestamp = (getCacheMillis() < 0 ? -1 :
                                   System.currentTimeMillis());
    final Resource resource = resolveResource(filename);
    if (resource.exists()) {
      long fileTimestamp = -1;
      if (getCacheMillis() >= 0) {
        // Last-modified timestamp of file will just be read if caching with timeout.
        try {
          fileTimestamp = resource.lastModified();
          if (propHolder != null
              && propHolder.getFileTimestamp() == fileTimestamp) {
            logger.debug("Re-caching properties for filename [{}] "
                + "- file hasn't been modified", filename);
            propHolder.setRefreshTimestamp(refreshTimestamp);
            return propHolder;
          }
        } catch (final IOException ex) {
          // Probably a class path resource: cache it forever.
          logger.debug("{} could not be resolved in the file system "
                  + "- assuming that it hasn't changed", resource, ex);
          fileTimestamp = -1;
        }
      }
      try {
        final Properties props = loadProperties(resource, filename);
        propHolder = new PropertiesHolder(props, fileTimestamp);
      } catch (final IOException ex) {
        logger.warn("Could not parse properties file [{}]", resource.getFilename(), ex);
        // Empty holder representing "not valid".
        propHolder = new PropertiesHolder();
      }
    } else {
      // Resource does not exist.
      logger.debug("No properties file found for [{}]", filename);
      // Empty holder representing "not found".
      propHolder = new PropertiesHolder();
    }
    propHolder.setRefreshTimestamp(refreshTimestamp);
    this.cachedProperties.put(filename, propHolder);
    return propHolder;
  }

  /**
   * Resolve the specified bundle {@code filename} into a concrete
   * {@link Resource}, potentially checking multiple sources or file
   * extensions.
   * <p>
   * If no suitable concrete {@code Resource} can be resolved, this method
   * returns a {@code Resource} for which {@link Resource#exists()} returns
   * {@code false}, which gets subsequently ignored.
   * <p>
   * This can be leveraged to check the last modification timestamp or to load
   * properties from alternative sources &mdash; for example, from an XML BLOB
   * in a database, or from properties serialized using a custom format such as
   * JSON.
   * <p>
   * The default implementation delegates to the configured
   * {@link #setResourceLoader(ResourceLoader) ResourceLoader} to resolve
   * resources, checking in order for existing {@code Resource} with extensions
   * defined by {@link #setFileExtensions(List)} ({@code .properties} and
   * {@code .xml} by default).
   * <p>
   * When overriding this method, {@link #loadProperties(Resource, String)}
   * <strong>must</strong> be capable of loading properties from any type of
   * {@code Resource} returned by this method. As a consequence, implementors
   * are strongly encouraged to also override {@code loadProperties()}.
   * <p>
   * As an alternative to overriding this method, you can configure a
   * {@link #setPropertiesPersister(PropertiesPersister) PropertiesPersister}
   * that is capable of dealing with all resources returned by this method.
   * Please note, however, that the default {@code loadProperties()}
   * implementation uses
   * {@link PropertiesPersister#loadFromXml(Properties, InputStream)
   * loadFromXml} for XML resources and otherwise uses the two
   * {@link PropertiesPersister#load(Properties, InputStream) load} methods for
   * other types of resources.
   *
   * @param filename
   *     the bundle filename (basename + Locale)
   * @return the {@code Resource} to use
   */
  protected Resource resolveResource(final String filename) {
    Resource resource = null;
    for (final String fileExtension : this.fileExtensions) {
      resource = this.resourceLoader.getResource(filename + fileExtension);
      if (resource.exists()) {
        return resource;
      }
    }
    return Objects.requireNonNull(resource);
  }

  /**
   * Load the properties from the given resource.
   *
   * @param resource
   *     the resource to load from
   * @param filename
   *     the original bundle filename (basename + Locale)
   * @return the populated Properties instance
   * @throws IOException
   *     if properties loading failed
   */
  protected Properties loadProperties(final Resource resource, final String filename)
      throws IOException {
    final Properties props = newProperties();
    try (final InputStream is = resource.getInputStream()) {
      final String resourceFilename = resource.getFilename();
      if (resourceFilename != null && resourceFilename.endsWith(XML_EXTENSION)) {
        logger.debug("Loading properties [{}]", resource.getFilename());
        this.propertiesPersister.loadFromXml(props, is);
      } else {
        String encoding = null;
        if (this.fileEncodings != null) {
          encoding = this.fileEncodings.getProperty(filename);
        }
        if (encoding == null) {
          encoding = getDefaultEncoding();
        }
        if (encoding != null) {
          logger.debug("Loading properties [{}] with encoding '{}'",
              resource.getFilename(), encoding);
          this.propertiesPersister.load(props, new InputStreamReader(is, encoding));
        } else {
          logger.debug("Loading properties [{}]", resource.getFilename());
          this.propertiesPersister.load(props, is);
        }
      }
      return props;
    }
  }

  /**
   * Template method for creating a plain new {@link Properties} instance. The
   * default implementation simply calls {@link Properties#Properties()}.
   * <p>
   * Allows for returning a custom {@link Properties} extension in subclasses.
   * Overriding methods should just instantiate a custom {@link Properties}
   * subclass, with no further initialization or population to be performed at
   * that point.
   *
   * @return a plain Properties instance
   */
  protected Properties newProperties() {
    return new Properties();
  }

  /**
   * Clear the resource bundle cache. Subsequent resolve calls will lead to
   * reloading of the properties files.
   */
  public void clearCache() {
    logger.debug("Clearing entire resource bundle cache");
    this.cachedProperties.clear();
    this.cachedMergedProperties.clear();
  }

  /**
   * Clear the resource bundle caches of this MessageSource and all its
   * ancestors.
   *
   * @see #clearCache
   */
  public void clearCacheIncludingAncestors() {
    clearCache();
    final MessageSource parent = getParentMessageSource();
    if (parent instanceof ReloadableResourceBundleMessageSource) {
      ((ReloadableResourceBundleMessageSource) parent).clearCacheIncludingAncestors();
    }
  }

  @Override
  public String toString() {
    return getClass().getName() + ": basenames=" + getBasenameSet();
  }

  /**
   * PropertiesHolder for caching. Stores the last-modified timestamp of the
   * source file for efficient change detection, and the timestamp of the last
   * refresh attempt (updated every time the cache entry gets re-validated).
   */
  protected class PropertiesHolder {

    @Nullable
    private final Properties properties;

    private final long fileTimestamp;

    private volatile long refreshTimestamp = -2;

    private final ReentrantLock refreshLock = new ReentrantLock();

    /**
     * Cache to hold already generated MessageFormats per message code.
     */
    private final ConcurrentMap<String, Map<Locale, MessageFormat>>
        cachedMessageFormats = new ConcurrentHashMap<>();

    public PropertiesHolder() {
      this.properties = null;
      this.fileTimestamp = -1;
    }

    public PropertiesHolder(@Nullable final Properties properties, final long fileTimestamp) {
      this.properties = properties;
      this.fileTimestamp = fileTimestamp;
    }

    @Nullable
    public Properties getProperties() {
      return this.properties;
    }

    public long getFileTimestamp() {
      return this.fileTimestamp;
    }

    public void setRefreshTimestamp(final long refreshTimestamp) {
      this.refreshTimestamp = refreshTimestamp;
    }

    public long getRefreshTimestamp() {
      return this.refreshTimestamp;
    }

    @Nullable
    public String getProperty(final String code) {
      if (this.properties == null) {
        return null;
      }
      return this.properties.getProperty(code);
    }

    @Nullable
    public MessageFormat getMessageFormat(final String code, final Locale locale) {
      if (this.properties == null) {
        return null;
      }
      Map<Locale, MessageFormat> localeMap = this.cachedMessageFormats.get(code);
      if (localeMap != null) {
        final MessageFormat result = localeMap.get(locale);
        if (result != null) {
          return result;
        }
      }
      final String msg = this.properties.getProperty(code);
      if (msg != null) {
        if (localeMap == null) {
          localeMap = new ConcurrentHashMap<>();
          final Map<Locale, MessageFormat> existing = this.cachedMessageFormats
              .putIfAbsent(code, localeMap);
          if (existing != null) {
            localeMap = existing;
          }
        }
        final MessageFormat result = createMessageFormat(msg, locale);
        localeMap.put(locale, result);
        return result;
      }
      return null;
    }
  }

}
