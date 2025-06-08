////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.config.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.Reader;
import java.io.Serial;
import java.io.Writer;
import java.net.URI;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Map;
import java.util.Properties;

import javax.annotation.concurrent.ThreadSafe;

import ltd.qubit.commons.config.Config;
import ltd.qubit.commons.config.error.ConfigurationError;
import ltd.qubit.commons.io.IoUtils;
import ltd.qubit.commons.net.Url;
import ltd.qubit.commons.util.properties.PropertiesUtils;

import static java.nio.charset.StandardCharsets.UTF_8;

import static ltd.qubit.commons.lang.Argument.requireNonNull;

/**
 * 一个{@link Config}对象，用于从Java属性文件加载/存储配置。
 *
 * @author 胡海星
 */
@ThreadSafe
public class PropertiesConfig extends DefaultConfig {

  @Serial
  private static final long serialVersionUID = 6463503499725651996L;

  /**
   * 属性文件的默认字符集。
   */
  public static final Charset DEFAULT_CHARSET = UTF_8;

  private volatile Charset charset;

  /**
   * 使用默认字符集构造一个空的{@link PropertiesConfig}。
   */
  public PropertiesConfig() {
    charset = DEFAULT_CHARSET;
  }

  /**
   * 使用指定的字符集构造一个空的{@link PropertiesConfig}。
   *
   * @param charset
   *     属性文件的字符集。
   */
  public PropertiesConfig(final Charset charset) {
    this.charset = requireNonNull("charset", charset);
  }

  /**
   * 构造一个{@link PropertiesConfig}。
   *
   * @param resource
   *     要加载配置的属性资源的路径。
   * @throws ConfigurationError
   *     如果发生任何错误。
   */
  public PropertiesConfig(final String resource) {
    charset = DEFAULT_CHARSET;
    load(resource);
  }

  /**
   * 构造一个{@link PropertiesConfig}。
   *
   * @param resource
   *     要加载配置的属性资源的路径。
   * @param charset
   *     属性文件的字符集。
   * @throws ConfigurationError
   *     如果发生任何错误。
   */
  public PropertiesConfig(final String resource, final Charset charset) {
    this.charset = requireNonNull("charset", charset);
    load(resource);
  }

  /**
   * 构造一个{@link PropertiesConfig}。
   *
   * @param resource
   *     要加载配置的属性资源的路径。
   * @param loaderClass
   *     用于加载资源的类。
   * @throws ConfigurationError
   *     如果发生任何错误。
   */
  public PropertiesConfig(final String resource, final Class<?> loaderClass) {
    charset = DEFAULT_CHARSET;
    load(resource, loaderClass);
  }

  /**
   * 构造一个{@link PropertiesConfig}。
   *
   * @param resource
   *     要加载配置的属性资源的路径。
   * @param loaderClass
   *     用于加载资源的类。
   * @param charset
   *     属性文件的字符集。
   * @throws ConfigurationError
   *     如果发生任何错误。
   */
  public PropertiesConfig(final String resource, final Class<?> loaderClass,
      final Charset charset) {
    this.charset = requireNonNull("charset", charset);
    load(resource, loaderClass);
  }

  /**
   * 构造一个{@link PropertiesConfig}。
   *
   * @param resource
   *     要加载配置的属性资源的路径。
   * @param loader
   *     用于加载资源的类加载器。
   * @throws ConfigurationError
   *     如果发生任何错误。
   */
  public PropertiesConfig(final String resource, final ClassLoader loader) {
    charset = DEFAULT_CHARSET;
    load(resource, loader);
  }

  /**
   * 构造一个{@link PropertiesConfig}。
   *
   * @param resource
   *     要加载配置的属性资源的路径。
   * @param loader
   *     用于加载资源的类加载器。
   * @param charset
   *     属性文件的字符集。
   * @throws ConfigurationError
   *     如果发生任何错误。
   */
  public PropertiesConfig(final String resource, final ClassLoader loader,
      final Charset charset) {
    this.charset = requireNonNull("charset", charset);
    load(resource, loader);
  }

  /**
   * 构造一个{@link PropertiesConfig}。
   *
   * @param url
   *     要加载配置的属性资源的URL。
   * @throws ConfigurationError
   *     如果发生任何错误。
   */
  public PropertiesConfig(final Url url) {
    charset = DEFAULT_CHARSET;
    load(url);
  }

  /**
   * 构造一个{@link PropertiesConfig}。
   *
   * @param url
   *     要加载配置的属性资源的URL。
   * @param charset
   *     属性文件的字符集。
   * @throws ConfigurationError
   *     如果发生任何错误。
   */
  public PropertiesConfig(final Url url, final Charset charset) {
    this.charset = requireNonNull("charset", charset);
    load(url);
  }

  /**
   * 构造一个{@link PropertiesConfig}。
   *
   * @param url
   *     要加载配置的属性资源的URL。
   * @throws ConfigurationError
   *     如果发生任何错误。
   */
  public PropertiesConfig(final URL url) {
    charset = DEFAULT_CHARSET;
    load(url);
  }

  /**
   * 构造一个{@link PropertiesConfig}。
   *
   * @param url
   *     要加载配置的属性资源的URL。
   * @param charset
   *     属性文件的字符集。
   * @throws ConfigurationError
   *     如果发生任何错误。
   */
  public PropertiesConfig(final URL url, final Charset charset) {
    this.charset = requireNonNull("charset", charset);
    load(url);
  }

  /**
   * 构造一个{@link PropertiesConfig}。
   *
   * @param uri
   *     要加载配置的属性资源的URI。
   * @throws ConfigurationError
   *     如果发生任何错误。
   */
  public PropertiesConfig(final URI uri) {
    charset = DEFAULT_CHARSET;
    load(uri);
  }

  /**
   * 构造一个{@link PropertiesConfig}。
   *
   * @param uri
   *     要加载配置的属性资源的URI。
   * @param charset
   *     属性文件的字符集。
   * @throws ConfigurationError
   *     如果发生任何错误。
   */
  public PropertiesConfig(final URI uri, final Charset charset) {
    this.charset = requireNonNull("charset", charset);
    load(uri);
  }

  /**
   * 构造一个{@link PropertiesConfig}。
   *
   * @param file
   *     要加载配置的属性文件。
   * @throws ConfigurationError
   *     如果发生任何错误。
   */
  public PropertiesConfig(final File file) {
    charset = DEFAULT_CHARSET;
    load(file);
  }

  /**
   * 构造一个{@link PropertiesConfig}。
   *
   * @param file
   *     要加载配置的属性文件。
   * @param charset
   *     属性文件的字符集。
   * @throws ConfigurationError
   *     如果发生任何错误。
   */
  public PropertiesConfig(final File file, final Charset charset) {
    this.charset = requireNonNull("charset", charset);
    load(file);
  }

  /**
   * 构造一个{@link PropertiesConfig}。
   *
   * @param in
   *     从中加载配置的属性文件的输入流。
   * @throws ConfigurationError
   *     如果发生任何错误。
   */
  public PropertiesConfig(final InputStream in) {
    charset = DEFAULT_CHARSET;
    load(in);
  }

  /**
   * 构造一个{@link PropertiesConfig}。
   *
   * @param in
   *     从中加载配置的属性文件的输入流。
   * @param charset
   *     属性文件的字符集。
   * @throws ConfigurationError
   *     如果发生任何错误。
   */
  public PropertiesConfig(final InputStream in, final Charset charset) {
    this.charset = requireNonNull("charset", charset);
    load(in);
  }

  /**
   * 构造一个{@link PropertiesConfig}。
   *
   * @param reader
   *     从中加载配置的属性文件的读取器。
   * @throws ConfigurationError
   *     如果发生任何错误。
   */
  public PropertiesConfig(final Reader reader) {
    charset = DEFAULT_CHARSET;
    load(reader);
  }

  /**
   * 构造一个{@link PropertiesConfig}。
   *
   * @param reader
   *     从中加载配置的属性文件的读取器。
   * @param charset
   *     属性文件的字符集。
   * @throws ConfigurationError
   *     如果发生任何错误。
   */
  public PropertiesConfig(final Reader reader, final Charset charset) {
    this.charset = requireNonNull("charset", charset);
    load(reader);
  }

  /**
   * 获取此属性文件的字符集。
   *
   * @return 此属性文件的字符集。
   */
  public Charset getCharset() {
    return charset;
  }

  /**
   * 设置此属性文件的字符集。
   *
   * @param charset
   *     要设置的新字符集。
   */
  public void setCharset(final Charset charset) {
    this.charset = requireNonNull("charset", charset);
  }

  /**
   * 从指定的资源加载此配置。
   *
   * @param resource
   *     指定资源的路径。
   * @throws ConfigurationError
   *     如果发生任何I/O错误。
   */
  public synchronized void load(final String resource) {
    logger.debug("Loading properties configuration from {}", resource);
    try {
      final Properties props = PropertiesUtils.load(resource, charset);
      load(props);
    } catch (final IOException e) {
      throw new ConfigurationError(e);
    }
  }

  /**
   * 从指定的资源加载此配置。
   *
   * @param resource
   *     指定资源的路径。
   * @param loaderClass
   *     用于加载资源的类。
   * @throws ConfigurationError
   *     如果发生任何I/O错误。
   */
  public synchronized void load(final String resource, final Class<?> loaderClass) {
    logger.debug("Loading properties configuration from {}", resource);
    try {
      final Properties props = PropertiesUtils.load(resource, loaderClass, charset);
      load(props);
    } catch (final IOException e) {
      throw new ConfigurationError(e);
    }
  }

  /**
   * 从指定的资源加载此配置。
   *
   * @param resource
   *     指定资源的路径。
   * @param loader
   *     用于加载资源的类加载器。
   * @throws ConfigurationError
   *     如果发生任何I/O错误。
   */
  public synchronized void load(final String resource, final ClassLoader loader) {
    logger.debug("Loading properties configuration from {}", resource);
    try {
      final Properties props = PropertiesUtils.load(resource, loader, charset);
      load(props);
    } catch (final IOException e) {
      throw new ConfigurationError(e);
    }
  }

  /**
   * 从指定的URL加载此配置。
   *
   * @param url
   *     指定的URL。
   * @throws ConfigurationError
   *     如果发生任何I/O错误。
   */
  public synchronized void load(final Url url) {
    logger.debug("Loading properties configuration from {}", url);
    try {
      final Properties props = PropertiesUtils.load(url, charset);
      load(props);
    } catch (final IOException e) {
      throw new ConfigurationError(e);
    }
  }

  /**
   * 从指定的URL加载此配置。
   *
   * @param url
   *     指定的URL。
   * @throws ConfigurationError
   *     如果发生任何I/O错误。
   */
  public synchronized void load(final URL url) {
    logger.debug("Loading properties configuration from {}", url);
    try {
      final Properties props = PropertiesUtils.load(url, charset);
      load(props);
    } catch (final IOException e) {
      throw new ConfigurationError(e);
    }
  }

  /**
   * 从指定的URI加载此配置。
   *
   * @param uri
   *     指定的URI。
   * @throws ConfigurationError
   *     如果发生任何I/O错误。
   */
  public synchronized void load(final URI uri) {
    try {
      final Properties props = PropertiesUtils.load(uri, charset);
      load(props);
    } catch (final IOException e) {
      throw new ConfigurationError(e);
    }
  }

  /**
   * 从指定的文件加载此配置。
   *
   * @param file
   *     指定的文件。
   * @throws ConfigurationError
   *     如果发生任何I/O错误。
   */
  public synchronized void load(final File file) {
    logger.debug("Loading properties configuration from {}", file);
    try {
      final Properties props = PropertiesUtils.load(file, charset);
      load(props);
    } catch (final IOException e) {
      throw new ConfigurationError(e);
    }
  }

  /**
   * 从指定的输入流加载此配置。
   * <p>
   * 此函数将<b>不</b>关闭指定的输入流。
   *
   * @param in
   *     指定的输入流。
   * @throws ConfigurationError
   *     如果发生任何I/O错误。
   */
  public synchronized void load(final InputStream in) {
    logger.debug("Loading properties configuration from {}", in);
    try {
      final Properties props = PropertiesUtils.load(in, charset);
      load(props);
    } catch (final IOException e) {
      throw new ConfigurationError(e);
    }
  }

  /**
   * 从指定的读取器加载此配置。
   * <p>
   * 此函数将<b>不</b>关闭指定的读取器。
   *
   * @param reader
   *     指定的读取器。
   * @throws ConfigurationError
   *     如果发生任何I/O错误。
   */
  public synchronized void load(final Reader reader) {
    logger.debug("Loading properties configuration from {}", reader);
    try {
      final Properties props = new Properties();
      props.load(reader);
      load(props);
    } catch (final IOException e) {
      throw new ConfigurationError(e);
    }
  }

  /**
   * 从指定的{@link Properties}对象加载此配置。
   *
   * @param properties
   *     指定的{@link Properties}对象。
   */
  public synchronized void load(final Properties properties) {
    clear();
    for (final Map.Entry<Object, Object> entry : properties.entrySet()) {
      final String name = (String) entry.getKey();
      final String value = (String) entry.getValue();
      addString(name, value);
    }
  }

  /**
   * 将此配置存储到指定的输出流。
   *
   * @param out
   *     指定的输出流。
   * @throws ConfigurationError
   *     如果发生任何I/O错误。
   */
  public synchronized void store(final OutputStream out) {
    logger.debug("Storing properties configuration from {}", out);
    final Properties props = store();
    try {
      PropertiesUtils.store(props, out, charset, description);
    } catch (final IOException e) {
      throw new ConfigurationError(e);
    }
  }

  /**
   * 将此配置存储到指定的打印流。
   *
   * @param out
   *     指定的打印流。
   * @throws ConfigurationError
   *     如果发生任何I/O错误。
   */
  public synchronized void store(final PrintStream out) {
    logger.debug("Storing properties configuration from {}", out);
    final Properties props = store();
    try {
      PropertiesUtils.store(props, out, charset, description);
    } catch (final IOException e) {
      throw new ConfigurationError(e);
    }
  }

  /**
   * 将此配置存储到指定的写入器。
   *
   * @param writer
   *     指定的写入器。
   * @throws ConfigurationError
   *     如果发生任何I/O错误。
   */
  public synchronized void store(final Writer writer) {
    logger.debug("Storing properties configuration from {}", writer);
    final Properties props = store();
    try {
      props.store(writer, description);
    } catch (final IOException e) {
      throw new ConfigurationError(e);
    } finally {
      IoUtils.closeQuietly(writer);
    }
  }

  /**
   * 将此配置存储到指定的文件。
   *
   * @param file
   *     指定的文件。
   * @throws ConfigurationError
   *     如果发生任何I/O错误。
   */
  public synchronized void store(final File file) {
    logger.debug("Storing properties configuration from {}", file);
    final Properties props = store();
    OutputStream out = null;
    try {
      out = new FileOutputStream(file);
      props.store(out, description);
    } catch (final IOException e) {
      throw new ConfigurationError(e);
    } finally {
      IoUtils.closeQuietly(out);
    }
  }

  /**
   * 将此配置存储到{@link Properties}对象中。
   *
   * @return 存储此配置的{@link Properties}对象。
   */
  public synchronized Properties store() {
    final Properties result = new Properties();
    for (final Map.Entry<String, DefaultProperty> entry : properties.entrySet()) {
      final String name = entry.getKey();
      final DefaultProperty property = entry.getValue();
      result.setProperty(name, property.getValueAsString());
    }
    return result;
  }
}
