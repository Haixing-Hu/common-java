////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.i18n.message;

import java.util.LinkedHashSet;
import java.util.Locale;
import java.util.Set;

import javax.annotation.Nullable;

import ltd.qubit.commons.lang.ArrayUtils;

import static ltd.qubit.commons.lang.StringUtils.isEmptyOrBlank;

/**
 * Abstract base class for {@code MessageSource} implementations based on
 * resource bundle conventions, such as {@link ResourceBundleMessageSource} and
 * {@link ReloadableResourceBundleMessageSource}. Provides common configuration
 * methods and corresponding semantic definitions.
 * <p>
 * This class is a copy of
 * {@code org.springframework.context.support.AbstractResourceBasedMessageSource}
 * with slight modifications. It is used to avoid the dependency of Spring Framework.
 *
 * @author Juergen Hoeller
 * @author Haixing Hu
 * @see ResourceBundleMessageSource
 * @see ReloadableResourceBundleMessageSource
 */
public abstract class AbstractResourceBasedMessageSource
    extends AbstractMessageSource {

  private final Set<String> basenameSet = new LinkedHashSet<>(4);

  @Nullable
  private String defaultEncoding;

  private boolean fallbackToSystemLocale = true;

  @Nullable
  private Locale defaultLocale;

  private long cacheMillis = -1;

  /**
   * Set a single basename, following the basic ResourceBundle convention of not
   * specifying file extension or language codes. The resource location format
   * is up to the specific {@code MessageSource} implementation.
   * <p>
   * Regular and XMl properties files are supported: e.g. "messages" will find
   * a "messages.properties", "messages_en.properties" etc arrangement as well
   * as "messages.xml", "messages_en.xml" etc.
   *
   * @param basename
   *     the single basename
   * @see #setBasenames
   * @see org.springframework.core.io.ResourceEditor
   * @see java.util.ResourceBundle
   */
  public void setBasename(final String basename) {
    setBasenames(basename);
  }

  /**
   * Set an array of basenames, each following the basic ResourceBundle
   * convention of not specifying file extension or language codes. The resource
   * location format is up to the specific {@code MessageSource}
   * implementation.
   * <p>Regular and XMl properties files are supported: e.g. "messages" will
   * find
   * a "messages.properties", "messages_en.properties" etc arrangement as well
   * as "messages.xml", "messages_en.xml" etc.
   * <p>The associated resource bundles will be checked sequentially when
   * resolving
   * a message code. Note that message definitions in a <i>previous</i> resource
   * bundle will override ones in a later bundle, due to the sequential lookup.
   * <p>Note: In contrast to {@link #addBasenames}, this replaces existing
   * entries
   * with the given names and can therefore also be used to reset the
   * configuration.
   *
   * @param basenames
   *     an array of basenames
   * @see #setBasename
   * @see java.util.ResourceBundle
   */
  public void setBasenames(final String... basenames) {
    this.basenameSet.clear();
    addBasenames(basenames);
  }

  /**
   * Add the specified basenames to the existing basename configuration.
   * <p>
   * Note: If a given basename already exists, the position of its entry
   * will remain as in the original set. New entries will be added at the end of
   * the list, to be searched after existing basenames.
   *
   * @see #setBasenames
   * @see java.util.ResourceBundle
   */
  public void addBasenames(final String... basenames) {
    if (!ArrayUtils.isEmpty(basenames)) {
      for (final String basename : basenames) {
        if (isEmptyOrBlank(basename)) {
          throw new IllegalArgumentException("Basename must not be empty nor blank");
        }
        basenameSet.add(basename.strip());
      }
    }
  }

  /**
   * Return this {@code MessageSource}'s basename set, containing entries in the
   * order of registration.
   * <p>
   * Calling code may introspect this set as well as add or remove entries.
   *
   * @see #addBasenames
   * @since 4.3
   */
  public Set<String> getBasenameSet() {
    return this.basenameSet;
  }

  /**
   * Set the default charset to use for parsing properties files. Used if no
   * file-specific charset is specified for a file.
   * <p>The effective default is the {@code java.util.Properties}
   * default encoding: ISO-8859-1. A {@code null} value indicates the platform
   * default encoding.
   * <p>Only applies to classic properties files, not to XML files.
   *
   * @param defaultEncoding
   *     the default charset
   */
  public void setDefaultEncoding(@Nullable final String defaultEncoding) {
    this.defaultEncoding = defaultEncoding;
  }

  /**
   * Return the default charset to use for parsing properties files, if any.
   *
   * @since 4.3
   */
  @Nullable
  protected String getDefaultEncoding() {
    return this.defaultEncoding;
  }

  /**
   * Set whether to fall back to the system Locale if no files for a specific
   * Locale have been found. Default is "true"; if this is turned off, the only
   * fallback will be the default file (e.g. "messages.properties" for basename
   * "messages").
   * <p>Falling back to the system Locale is the default behavior of
   * {@code java.util.ResourceBundle}. However, this is often not desirable in
   * an application server environment, where the system Locale is not relevant
   * to the application at all: set this flag to "false" in such a scenario.
   *
   * @see #setDefaultLocale
   */
  public void setFallbackToSystemLocale(final boolean fallbackToSystemLocale) {
    this.fallbackToSystemLocale = fallbackToSystemLocale;
  }

  /**
   * Return whether to fall back to the system Locale if no files for a specific
   * Locale have been found.
   *
   * @since 4.3
   * @deprecated as of 5.2.2, in favor of {@link #getDefaultLocale()}
   */
  @Deprecated
  protected boolean isFallbackToSystemLocale() {
    return this.fallbackToSystemLocale;
  }

  /**
   * Specify a default Locale to fall back to, as an alternative to falling back
   * to the system Locale.
   * <p>Default is to fall back to the system Locale. You may override this
   * with
   * a locally specified default Locale here, or enforce no fallback locale at
   * all through disabling
   * {@link #setFallbackToSystemLocale "fallbackToSystemLocale"}.
   *
   * @see #setFallbackToSystemLocale
   * @see #getDefaultLocale()
   * @since 5.2.2
   */
  public void setDefaultLocale(@Nullable final Locale defaultLocale) {
    this.defaultLocale = defaultLocale;
  }

  /**
   * Determine a default Locale to fall back to: either a locally specified
   * default Locale or the system Locale, or {@code null} for no fallback locale
   * at all.
   *
   * @see #setDefaultLocale
   * @see #setFallbackToSystemLocale
   * @see Locale#getDefault()
   * @since 5.2.2
   */
  @Nullable
  protected Locale getDefaultLocale() {
    if (this.defaultLocale != null) {
      return this.defaultLocale;
    }
    if (this.fallbackToSystemLocale) {
      return Locale.getDefault();
    }
    return null;
  }

  /**
   * Set the number of seconds to cache loaded properties files.
   * <ul>
   * <li>Default is "-1", indicating to cache forever (matching the default behavior
   * of {@code java.util.ResourceBundle}). Note that this constant follows Spring
   * conventions, not {@link java.util.ResourceBundle.Control#getTimeToLive}.
   * <li>A positive number will cache loaded properties files for the given
   * number of seconds. This is essentially the interval between refresh checks.
   * Note that a refresh attempt will first check the last-modified timestamp
   * of the file before actually reloading it; so if files don't change, this
   * interval can be set rather low, as refresh attempts will not actually reload.
   * <li>A value of "0" will check the last-modified timestamp of the file on
   * every message access. <b>Do not use this in a production environment!</b>
   * </ul>
   * <p><b>Note that depending on your ClassLoader, expiration might not work reliably
   * since the ClassLoader may hold on to a cached version of the bundle file.</b>
   * Prefer {@link ReloadableResourceBundleMessageSource} over
   * {@link ResourceBundleMessageSource} in such a scenario, in combination with
   * a non-classpath location.
   */
  public void setCacheSeconds(final int cacheSeconds) {
    this.cacheMillis = cacheSeconds * 1000L;
  }

  /**
   * Set the number of milliseconds to cache loaded properties files. Note that
   * it is common to set seconds instead: {@link #setCacheSeconds}.
   * <ul>
   * <li>Default is "-1", indicating to cache forever (matching the default behavior
   * of {@code java.util.ResourceBundle}). Note that this constant follows Spring
   * conventions, not {@link java.util.ResourceBundle.Control#getTimeToLive}.
   * <li>A positive number will cache loaded properties files for the given
   * number of milliseconds. This is essentially the interval between refresh checks.
   * Note that a refresh attempt will first check the last-modified timestamp
   * of the file before actually reloading it; so if files don't change, this
   * interval can be set rather low, as refresh attempts will not actually reload.
   * <li>A value of "0" will check the last-modified timestamp of the file on
   * every message access. <b>Do not use this in a production environment!</b>
   * </ul>
   *
   * @see #setCacheSeconds
   * @since 4.3
   */
  public void setCacheMillis(final long cacheMillis) {
    this.cacheMillis = cacheMillis;
  }

  /**
   * Return the number of milliseconds to cache loaded properties files.
   *
   * @since 4.3
   */
  protected long getCacheMillis() {
    return this.cacheMillis;
  }

}
