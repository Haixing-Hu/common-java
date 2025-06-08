////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
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
 * 基于资源包约定的 {@code MessageSource} 实现的抽象基类，
 * 如 {@link ResourceBundleMessageSource} 和 {@link ReloadableResourceBundleMessageSource}。
 * 提供通用的配置方法和相应的语义定义。
 * <p>
 * 此类是 {@code org.springframework.context.support.AbstractResourceBasedMessageSource} 的副本，
 * 稍作修改。它用于避免对 Spring Framework 的依赖。
 *
 * @author Juergen Hoeller
 * @author 胡海星
 * @see ResourceBundleMessageSource
 * @see ReloadableResourceBundleMessageSource
 */
public abstract class AbstractResourceBasedMessageSource
    extends AbstractMessageSource {

  private final Set<String> basenameSet = new LinkedHashSet<>(4);

  /**
   * 默认编码。
   */
  @Nullable
  private String defaultEncoding;

  /**
   * 是否回退到系统 Locale。
   */
  private boolean fallbackToSystemLocale = true;

  /**
   * 默认 Locale。
   */
  @Nullable
  private Locale defaultLocale;

  /**
   * 缓存毫秒数。
   */
  private long cacheMillis = -1;

  /**
   * 设置单个基础名称，遵循不指定文件扩展名或语言代码的基本 ResourceBundle 约定。
   * 资源位置格式取决于具体的 {@code MessageSource} 实现。
   * <p>
   * 支持常规和 XML 属性文件：例如 "messages" 将查找
   * "messages.properties"、"messages_en.properties" 等排列以及
   * "messages.xml"、"messages_en.xml" 等。
   *
   * @param basename
   *     单个基础名称
   * @see #setBasenames
   * @see org.springframework.core.io.ResourceEditor
   * @see java.util.ResourceBundle
   */
  public void setBasename(final String basename) {
    setBasenames(basename);
  }

  /**
   * 设置基础名称数组，每个都遵循不指定文件扩展名或语言代码的基本 ResourceBundle 约定。
   * 资源位置格式取决于具体的 {@code MessageSource} 实现。
   * <p>支持常规和 XML 属性文件：例如 "messages" 将查找
   * "messages.properties"、"messages_en.properties" 等排列以及
   * "messages.xml"、"messages_en.xml" 等。
   * <p>在解析消息代码时，将按顺序检查关联的资源包。
   * 请注意，<i>前一个</i>资源包中的消息定义将覆盖后一个包中的定义，
   * 这是由于顺序查找造成的。
   * <p>注意：与 {@link #addBasenames} 相反，这会用给定的名称替换现有条目，
   * 因此也可以用于重置配置。
   *
   * @param basenames
   *     基础名称数组
   * @see #setBasename
   * @see java.util.ResourceBundle
   */
  public void setBasenames(final String... basenames) {
    this.basenameSet.clear();
    addBasenames(basenames);
  }

  /**
   * 将指定的基础名称添加到现有的基础名称配置中。
   * <p>
   * 注意：如果给定的基础名称已存在，其条目的位置将保持在原始集合中的位置。
   * 新条目将添加到列表的末尾，在现有基础名称之后搜索。
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
   * 返回此 {@code MessageSource} 的基础名称集合，包含按注册顺序排列的条目。
   * <p>
   * 调用代码可以内省此集合以及添加或删除条目。
   *
   * @see #addBasenames
   * @since 4.3
   */
  public Set<String> getBasenameSet() {
    return this.basenameSet;
  }

  /**
   * 设置用于解析属性文件的默认字符集。
   * 如果没有为文件指定特定的字符集，则使用此设置。
   * <p>有效默认值是 {@code java.util.Properties} 默认编码：ISO-8859-1。
   * {@code null} 值表示平台默认编码。
   * <p>仅适用于经典属性文件，不适用于 XML 文件。
   *
   * @param defaultEncoding
   *     默认字符集
   */
  public void setDefaultEncoding(@Nullable final String defaultEncoding) {
    this.defaultEncoding = defaultEncoding;
  }

  /**
   * 返回用于解析属性文件的默认字符集（如果有）。
   *
   * @since 4.3
   */
  @Nullable
  protected String getDefaultEncoding() {
    return this.defaultEncoding;
  }

  /**
   * 设置如果找不到特定 Locale 的文件时是否回退到系统 Locale。
   * 默认为 "true"；如果关闭此选项，唯一的回退将是默认文件
   *（例如基础名称 "messages" 对应的 "messages.properties"）。
   * <p>回退到系统 Locale 是 {@code java.util.ResourceBundle} 的默认行为。
   * 但是，这在应用服务器环境中通常是不可取的，因为系统 Locale 与应用程序完全不相关：
   * 在这种情况下，请将此标志设置为 "false"。
   *
   * @see #setDefaultLocale
   */
  public void setFallbackToSystemLocale(final boolean fallbackToSystemLocale) {
    this.fallbackToSystemLocale = fallbackToSystemLocale;
  }

  /**
   * 返回如果找不到特定 Locale 的文件时是否回退到系统 Locale。
   *
   * @since 4.3
   * @deprecated 自 5.2.2 起，支持 {@link #getDefaultLocale()}
   */
  @Deprecated
  protected boolean isFallbackToSystemLocale() {
    return this.fallbackToSystemLocale;
  }

  /**
   * 指定要回退到的默认 Locale，作为回退到系统 Locale 的替代方案。
   * <p>默认是回退到系统 Locale。您可以在此处用本地指定的默认 Locale 覆盖它，
   * 或者通过禁用 {@link #setFallbackToSystemLocale "fallbackToSystemLocale"}
   * 来完全不执行回退区域设置。
   *
   * @see #setFallbackToSystemLocale
   * @see #getDefaultLocale()
   * @since 5.2.2
   */
  public void setDefaultLocale(@Nullable final Locale defaultLocale) {
    this.defaultLocale = defaultLocale;
  }

  /**
   * 确定要回退到的默认 Locale：本地指定的默认 Locale 或系统 Locale，
   * 或 {@code null} 表示完全没有回退区域设置。
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
   * 设置缓存已加载属性文件的秒数。
   * <ul>
   * <li>默认为 "-1"，表示永久缓存（匹配 {@code java.util.ResourceBundle} 的默认行为）。
   * 请注意，此常量遵循 Spring 约定，而不是 {@link java.util.ResourceBundle.Control#getTimeToLive}。
   * <li>正数将缓存已加载的属性文件指定的秒数。这本质上是刷新检查之间的间隔。
   * 请注意，刷新尝试将首先检查文件的最后修改时间戳，然后再实际重新加载它；
   * 因此，如果文件不更改，此间隔可以设置得相当低，因为刷新尝试不会实际重新加载。
   * <li>值 "0" 将在每次消息访问时检查文件的最后修改时间戳。
   * <b>不要在生产环境中使用此选项！</b>
   * </ul>
   * <p><b>请注意，根据您的 ClassLoader，过期可能无法可靠工作，
   * 因为 ClassLoader 可能持有包文件的缓存版本。</b>
   * 在这种情况下，建议使用 {@link ReloadableResourceBundleMessageSource}
   * 而不是 {@link ResourceBundleMessageSource}，结合非类路径位置。
   */
  public void setCacheSeconds(final int cacheSeconds) {
    this.cacheMillis = cacheSeconds * 1000L;
  }

  /**
   * 设置缓存已加载属性文件的毫秒数。请注意，通常设置秒数：{@link #setCacheSeconds}。
   * <ul>
   * <li>默认为 "-1"，表示永久缓存（匹配 {@code java.util.ResourceBundle} 的默认行为）。
   * 请注意，此常量遵循 Spring 约定，而不是 {@link java.util.ResourceBundle.Control#getTimeToLive}。
   * <li>正数将缓存已加载的属性文件指定的毫秒数。这本质上是刷新检查之间的间隔。
   * 请注意，刷新尝试将首先检查文件的最后修改时间戳，然后再实际重新加载它；
   * 因此，如果文件不更改，此间隔可以设置得相当低，因为刷新尝试不会实际重新加载。
   * <li>值 "0" 将在每次消息访问时检查文件的最后修改时间戳。
   * <b>不要在生产环境中使用此选项！</b>
   * </ul>
   *
   * @see #setCacheSeconds
   * @since 4.3
   */
  public void setCacheMillis(final long cacheMillis) {
    this.cacheMillis = cacheMillis;
  }

  /**
   * 返回缓存已加载属性文件的毫秒数。
   *
   * @since 4.3
   */
  protected long getCacheMillis() {
    return this.cacheMillis;
  }

}
