////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
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
 * A {@link Config} object which loads/stores configurations from/to Java
 * properties files.
 *
 * @author Haixing Hu
 */
@ThreadSafe
public class PropertiesConfig extends DefaultConfig {

  @Serial
  private static final long serialVersionUID = 6463503499725651996L;

  /**
   * The default charset of the properties file.
   */
  public static final Charset DEFAULT_CHARSET = UTF_8;

  private volatile Charset charset;

  /**
   * Constructs an empty {@link PropertiesConfig}, with the default charset.
   */
  public PropertiesConfig() {
    charset = DEFAULT_CHARSET;
  }

  /**
   * Constructs an empty {@link PropertiesConfig}, with the specified charset.
   *
   * @param charset
   *     the charset of the properties file.
   */
  public PropertiesConfig(final Charset charset) {
    this.charset = requireNonNull("charset", charset);
  }

  /**
   * Constructs an {@link PropertiesConfig}.
   *
   * @param resource
   *     the path of the properties resource where to load the configuration.
   * @throws ConfigurationError
   *     if any error occurs.
   */
  public PropertiesConfig(final String resource) {
    charset = DEFAULT_CHARSET;
    load(resource);
  }

  /**
   * Constructs an {@link PropertiesConfig}.
   *
   * @param resource
   *     the path of the properties resource where to load the configuration.
   * @param charset
   *     the charset of the properties file.
   * @throws ConfigurationError
   *     if any error occurs.
   */
  public PropertiesConfig(final String resource, final Charset charset) {
    this.charset = requireNonNull("charset", charset);
    load(resource);
  }

  /**
   * Constructs an {@link PropertiesConfig}.
   *
   * @param resource
   *     the path of the properties resource where to load the configuration.
   * @param loaderClass
   *     the class used to load the resource.
   * @throws ConfigurationError
   *     if any error occurs.
   */
  public PropertiesConfig(final String resource, final Class<?> loaderClass) {
    charset = DEFAULT_CHARSET;
    load(resource, loaderClass);
  }

  /**
   * Constructs an {@link PropertiesConfig}.
   *
   * @param resource
   *     the path of the properties resource where to load the configuration.
   * @param loaderClass
   *     the class used to load the resource.
   * @param charset
   *     the charset of the properties file.
   * @throws ConfigurationError
   *     if any error occurs.
   */
  public PropertiesConfig(final String resource, final Class<?> loaderClass,
      final Charset charset) {
    this.charset = requireNonNull("charset", charset);
    load(resource, loaderClass);
  }

  /**
   * Constructs an {@link PropertiesConfig}.
   *
   * @param resource
   *     the path of the properties resource where to load the configuration.
   * @param loader
   *     the class loader use to load the resource.
   * @throws ConfigurationError
   *     if any error occurs.
   */
  public PropertiesConfig(final String resource, final ClassLoader loader) {
    charset = DEFAULT_CHARSET;
    load(resource, loader);
  }

  /**
   * Constructs an {@link PropertiesConfig}.
   *
   * @param resource
   *     the path of the properties resource where to load the configuration.
   * @param loader
   *     the class loader use to load the resource.
   * @param charset
   *     the charset of the properties file.
   * @throws ConfigurationError
   *     if any error occurs.
   */
  public PropertiesConfig(final String resource, final ClassLoader loader,
      final Charset charset) {
    this.charset = requireNonNull("charset", charset);
    load(resource, loader);
  }

  /**
   * Constructs an {@link PropertiesConfig}.
   *
   * @param url
   *     the URL of the properties resource where to load the configuration.
   * @throws ConfigurationError
   *     if any error occurs.
   */
  public PropertiesConfig(final Url url) {
    charset = DEFAULT_CHARSET;
    load(url);
  }

  /**
   * Constructs an {@link PropertiesConfig}.
   *
   * @param url
   *     the URL of the properties resource where to load the configuration.
   * @param charset
   *     the charset of the properties file.
   * @throws ConfigurationError
   *     if any error occurs.
   */
  public PropertiesConfig(final Url url, final Charset charset) {
    this.charset = requireNonNull("charset", charset);
    load(url);
  }

  /**
   * Constructs an {@link PropertiesConfig}.
   *
   * @param url
   *     the URL of the properties resource where to load the configuration.
   * @throws ConfigurationError
   *     if any error occurs.
   */
  public PropertiesConfig(final URL url) {
    charset = DEFAULT_CHARSET;
    load(url);
  }

  /**
   * Constructs an {@link PropertiesConfig}.
   *
   * @param url
   *     the URL of the properties resource where to load the configuration.
   * @param charset
   *     the charset of the properties file.
   * @throws ConfigurationError
   *     if any error occurs.
   */
  public PropertiesConfig(final URL url, final Charset charset) {
    this.charset = requireNonNull("charset", charset);
    load(url);
  }

  /**
   * Constructs an {@link PropertiesConfig}.
   *
   * @param uri
   *     the URI of the properties resource where to load the configuration.
   * @throws ConfigurationError
   *     if any error occurs.
   */
  public PropertiesConfig(final URI uri) {
    charset = DEFAULT_CHARSET;
    load(uri);
  }

  /**
   * Constructs an {@link PropertiesConfig}.
   *
   * @param uri
   *     the URI of the properties resource where to load the configuration.
   * @param charset
   *     the charset of the properties file.
   * @throws ConfigurationError
   *     if any error occurs.
   */
  public PropertiesConfig(final URI uri, final Charset charset) {
    this.charset = requireNonNull("charset", charset);
    load(uri);
  }

  /**
   * Constructs an {@link PropertiesConfig}.
   *
   * @param file
   *     the properties file where to load the configuration.
   * @throws ConfigurationError
   *     if any error occurs.
   */
  public PropertiesConfig(final File file) {
    charset = DEFAULT_CHARSET;
    load(file);
  }

  /**
   * Constructs an {@link PropertiesConfig}.
   *
   * @param file
   *     the properties file where to load the configuration.
   * @param charset
   *     the charset of the properties file.
   * @throws ConfigurationError
   *     if any error occurs.
   */
  public PropertiesConfig(final File file, final Charset charset) {
    this.charset = requireNonNull("charset", charset);
    load(file);
  }

  /**
   * Constructs an {@link PropertiesConfig}.
   *
   * <p>After calling this function, the input stream remains opened.
   *
   * @param in
   *     the input stream storing the properties where to load the
   *     configuration.
   * @throws ConfigurationError
   *     if any error occurs.
   */
  public PropertiesConfig(final InputStream in) {
    charset = DEFAULT_CHARSET;
    load(in);
  }

  /**
   * Constructs an {@link PropertiesConfig}.
   *
   * <p>After calling this function, the input stream remains opened.
   *
   * @param in
   *     the input stream storing the properties where to load the
   *     configuration.
   * @param charset
   *     the charset of the properties file.
   * @throws ConfigurationError
   *     if any error occurs.
   */
  public PropertiesConfig(final InputStream in, final Charset charset) {
    this.charset = requireNonNull("charset", charset);
    load(in);
  }

  /**
   * Constructs an {@link PropertiesConfig}.
   *
   * <p>After calling this function, the reader remains opened.
   *
   * @param reader
   *     the reader storing the properties where to load the configuration.
   * @throws ConfigurationError
   *     if any error occurs.
   */
  public PropertiesConfig(final Reader reader) {
    charset = DEFAULT_CHARSET;
    load(reader);
  }

  /**
   * Constructs an {@link PropertiesConfig}.
   *
   * <p>After calling this function, the reader remains opened.
   *
   * @param reader
   *     the reader storing the properties where to load the configuration.
   * @param charset
   *     the charset of the properties file.
   * @throws ConfigurationError
   *     if any error occurs.
   */
  public PropertiesConfig(final Reader reader, final Charset charset) {
    this.charset = requireNonNull("charset", charset);
    load(reader);
  }

  /**
   * Gets the charset.
   *
   * @return the charset.
   */
  public Charset getCharset() {
    return charset;
  }

  /**
   * Sets the charset.
   *
   * @param charset
   *     the new charset to set.
   */
  public void setCharset(final Charset charset) {
    this.charset = requireNonNull("charset", charset);
  }

  /**
   * Loads the configuration from an properties file.
   *
   * @param resource
   *     the path of the properties resource where to load the configuration.
   * @throws ConfigurationError
   *     if any error occurs.
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
   * Loads the configuration from an properties file.
   *
   * @param resource
   *     the path of the properties resource where to load the configuration.
   * @param loaderClass
   *     the class used to load the resource.
   * @throws ConfigurationError
   *     if any error occurs.
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
   * Loads the configuration from an properties file.
   *
   * @param resource
   *     the path of the properties resource where to load the configuration.
   * @param loader
   *     the class loader used to load the resource.
   * @throws ConfigurationError
   *     if any error occurs.
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
   * Loads the configuration from an properties file.
   *
   * @param url
   *     the URL of the properties resource where to load the configuration.
   * @throws ConfigurationError
   *     if any error occurs.
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
   * Loads the configuration from an properties file.
   *
   * @param url
   *     the URL of the properties resource where to load the configuration.
   * @throws ConfigurationError
   *     if any error occurs.
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
   * Loads the configuration from an properties file.
   *
   * @param uri
   *     the URI of the properties resource where to load the configuration.
   * @throws ConfigurationError
   *     if any error occurs.
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
   * Loads the configuration from an properties file.
   *
   * @param file
   *     the properties file where to load the configuration.
   * @throws ConfigurationError
   *     if any error occurs.
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
   * Loads the configuration from a properties read from an input stream.
   *
   * <p>After calling this function, the input stream remains opened.
   *
   * @param in
   *     the input stream of the properties file where to load the
   *     configuration.
   * @throws ConfigurationError
   *     if any error occurs.
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
   * Loads the configuration from a properties read from a reader.
   *
   * <p>After calling this function, the reader remains opened.
   *
   * @param reader
   *     the reader of the properties file where to load the configuration.
   * @throws ConfigurationError
   *     if any error occurs.
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
   * Loads the configuration from an properties.
   *
   * @param properties
   *     the properties where to load the configuration.
   * @throws ConfigurationError
   *     if any error occurs.
   */
  public synchronized void load(final Properties properties) {
    removeAll();
    for (final Map.Entry<Object, Object> entry : properties.entrySet()) {
      final String name = (String) entry.getKey();
      final String value = (String) entry.getValue();
      addString(name, value);
    }
  }

  /**
   * Stores this configuration to a output stream.
   *
   * <p>After calling this function, the stream is flushed but remains opened.
   *
   * @param out
   *     the output stream where to store the configuration.
   * @throws ConfigurationError
   *     if any error occurs.
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
   * Stores this configuration to a print stream.
   *
   * <p>After calling this function, the stream is flushed but remains opened.
   *
   * @param out
   *     the print stream where to store the configuration.
   * @throws ConfigurationError
   *     if any error occurs.
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
   * Stores this configuration to a writer.
   *
   * <p>After calling this function, the writer is flushed but remains opened.
   *
   * @param writer
   *     the writer where to store the configuration.
   * @throws ConfigurationError
   *     if any error occurs.
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
   * Stores this configuration to an properties file.
   *
   * @param file
   *     the properties file where to store the configuration.
   * @throws ConfigurationError
   *     if any error occurs.
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
   * Stores this configuration to a properties.
   *
   * @return the properties storing this configuration.
   */
  public synchronized Properties store() {
    final Properties result = new Properties();
    for (final Map.Entry<String, DefaultProperty> entry : properties
        .entrySet()) {
      final String name = entry.getKey();
      final DefaultProperty property = entry.getValue();
      result.setProperty(name, property.getValueAsString());
    }
    return result;
  }
}