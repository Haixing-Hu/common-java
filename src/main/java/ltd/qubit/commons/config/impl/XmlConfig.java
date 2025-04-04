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
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.Reader;
import java.io.Serial;
import java.io.Writer;
import java.net.URI;
import java.net.URL;

import javax.annotation.concurrent.ThreadSafe;

import ltd.qubit.commons.config.Config;
import ltd.qubit.commons.config.error.ConfigurationError;
import ltd.qubit.commons.io.serialize.XmlSerialization;
import ltd.qubit.commons.lang.SystemUtils;
import ltd.qubit.commons.net.Url;
import ltd.qubit.commons.text.xml.XmlException;

/**
 * A {@link Config} object which loads/stores configurations from/to XML files.
 *
 * @author Haixing Hu
 */
@ThreadSafe
public final class XmlConfig extends DefaultConfig {

  @Serial
  private static final long serialVersionUID = -2988726634410349164L;

  /**
   * Constructs an empty {@link XmlConfig}.
   */
  public XmlConfig() {
  }

  /**
   * Constructs an {@link XmlConfig}.
   *
   * @param resource
   *     the path of the XML resource where to load the configuration.
   * @throws ConfigurationError
   *     if any error occurs.
   */
  public XmlConfig(final String resource) {
    load(resource);
  }

  /**
   * Constructs an {@link XmlConfig}.
   *
   * @param resource
   *     the path of the XML resource where to load the configuration.
   * @param loaderClass
   *     the class used to load the resource.
   * @throws ConfigurationError
   *     if any error occurs.
   */
  public XmlConfig(final String resource, final Class<?> loaderClass) {
    load(resource, loaderClass);
  }

  /**
   * Constructs an {@link XmlConfig}.
   *
   * @param resource
   *     the path of the XML resource where to load the configuration.
   * @param loader
   *     the class loader use to load the resource.
   * @throws ConfigurationError
   *     if any error occurs.
   */
  public XmlConfig(final String resource, final ClassLoader loader) {
    load(resource, loader);
  }

  /**
   * Constructs an {@link XmlConfig}.
   *
   * @param url
   *     the URL of the XML resource where to load the configuration.
   * @throws ConfigurationError
   *     if any error occurs.
   */
  public XmlConfig(final Url url) {
    load(url);
  }

  /**
   * Constructs an {@link XmlConfig}.
   *
   * @param url
   *     the URL of the XML resource where to load the configuration.
   * @throws ConfigurationError
   *     if any error occurs.
   */
  public XmlConfig(final URL url) {
    load(url);
  }

  /**
   * Constructs an {@link XmlConfig}.
   *
   * @param uri
   *     the URI of the XML resource where to load the configuration.
   * @throws ConfigurationError
   *     if any error occurs.
   */
  public XmlConfig(final URI uri) {
    load(uri);
  }

  /**
   * Constructs an {@link XmlConfig}.
   *
   * @param file
   *     the XML file where to load the configuration.
   * @throws ConfigurationError
   *     if any error occurs.
   */
  public XmlConfig(final File file) {
    load(file);
  }

  /**
   * Constructs an {@link XmlConfig}.
   *
   * <p>After calling this function, the input stream remains opened.
   *
   * @param in
   *     the input stream storing the XML file where to load the configuration.
   * @throws ConfigurationError
   *     if any error occurs.
   */
  public XmlConfig(final InputStream in) {
    load(in);
  }

  /**
   * Constructs an {@link XmlConfig}.
   *
   * <p>After calling this function, the reader remains opened.
   *
   * @param reader
   *     the reader storing the XML file where to load the configuration.
   * @throws ConfigurationError
   *     if any error occurs.
   */
  public XmlConfig(final Reader reader) {
    load(reader);
  }

  /**
   * Loads the configuration from an XML file.
   *
   * @param resource
   *     the path of the XML resource where to load the configuration.
   * @throws ConfigurationError
   *     if any error occurs.
   */
  public synchronized void load(final String resource) {
    LOGGER.debug("Loading XML configuration from {}", resource);
    try {
      final URL url = SystemUtils.getResource(resource);
      if (url == null) {
        throw new ConfigurationError("Cannot find resource: " + resource);
      }
      final DefaultConfig config = XmlSerialization.deserialize(
          DefaultConfig.class, url);
      properties = config.properties;
    } catch (final XmlException e) {
      throw new ConfigurationError(e);
    }
  }

  /**
   * Loads the configuration from an XML file.
   *
   * @param resource
   *     the path of the XML resource where to load the configuration.
   * @param loaderClass
   *     the class used to load the resource.
   * @throws ConfigurationError
   *     if any error occurs.
   */
  public synchronized void load(final String resource, final Class<?> loaderClass) {
    LOGGER.debug("Loading XML configuration from {}", resource);
    try {
      final DefaultConfig config = XmlSerialization.deserialize(
          DefaultConfig.class, resource, loaderClass);
      properties = config.properties;
    } catch (final XmlException e) {
      throw new ConfigurationError(e);
    }
  }

  /**
   * Loads the configuration from an XML file.
   *
   * @param resource
   *     the path of the XML resource where to load the configuration.
   * @param loader
   *     the class loader used to load the resource.
   * @throws ConfigurationError
   *     if any error occurs.
   */
  public synchronized void load(final String resource, final ClassLoader loader) {
    LOGGER.debug("Loading XML configuration from {}", resource);
    try {
      final DefaultConfig config = XmlSerialization.deserialize(
          DefaultConfig.class, resource, loader);
      properties = config.properties;
    } catch (final XmlException e) {
      throw new ConfigurationError(e);
    }
  }

  /**
   * Loads the configuration from an XML file.
   *
   * @param url
   *     the URL of the XML resource where to load the configuration.
   * @throws ConfigurationError
   *     if any error occurs.
   */
  public synchronized void load(final Url url) {
    LOGGER.debug("Loading XML configuration from {}", url);
    try {
      final DefaultConfig config = XmlSerialization.deserialize(
          DefaultConfig.class, url);
      properties = config.properties;
    } catch (final XmlException e) {
      throw new ConfigurationError(e);
    }
  }

  /**
   * Loads the configuration from an XML file.
   *
   * @param url
   *     the URL of the XML resource where to load the configuration.
   * @throws ConfigurationError
   *     if any error occurs.
   */
  public synchronized void load(final URL url) {
    LOGGER.debug("Loading XML configuration from {}", url);
    try {
      final DefaultConfig config = XmlSerialization.deserialize(
          DefaultConfig.class, url);
      properties = config.properties;
    } catch (final XmlException e) {
      throw new ConfigurationError(e);
    }
  }

  /**
   * Loads the configuration from an XML file.
   *
   * @param uri
   *     the URI of the XML resource where to load the configuration.
   * @throws ConfigurationError
   *     if any error occurs.
   */
  public synchronized void load(final URI uri) {
    try {
      final DefaultConfig config = XmlSerialization.deserialize(
          DefaultConfig.class, uri);
      properties = config.properties;
    } catch (final XmlException e) {
      throw new ConfigurationError(e);
    }
  }

  /**
   * Loads the configuration from an XML file.
   *
   * @param file
   *     the XML file where to load the configuration.
   * @throws ConfigurationError
   *     if any error occurs.
   */
  public synchronized void load(final File file) {
    LOGGER.debug("Loading XML configuration from {}", file);
    try {
      final DefaultConfig config = XmlSerialization.deserialize(
          DefaultConfig.class, file);
      properties = config.properties;
    } catch (final XmlException e) {
      throw new ConfigurationError(e);
    }
  }

  /**
   * Loads the configuration from an XML file.
   *
   * <p>After calling this function, the input stream remains opened.
   *
   * @param in
   *     the input stream storing the XML file where to load the configuration.
   * @throws ConfigurationError
   *     if any error occurs.
   */
  public synchronized void load(final InputStream in) {
    LOGGER.debug("Loading XML configuration from {}", in);
    try {
      final DefaultConfig config = XmlSerialization.deserialize(
          DefaultConfig.class, in);
      properties = config.properties;
    } catch (final XmlException e) {
      throw new ConfigurationError(e);
    }
  }

  /**
   * Loads the configuration from an XML file.
   *
   * <p>After calling this function, the input stream remains opened.
   *
   * @param reader
   *     the reader storing the XML file where to load the configuration.
   * @throws ConfigurationError
   *     if any error occurs.
   */
  public synchronized void load(final Reader reader) {
    LOGGER.debug("Loading XML configuration from {}", reader);
    try {
      final DefaultConfig config = XmlSerialization.deserialize(
          DefaultConfig.class, reader);
      properties = config.properties;
    } catch (final XmlException e) {
      throw new ConfigurationError(e);
    }
  }

  /**
   * Stores this configuration to an output stream.
   *
   * <p>After calling this function, the stream is flushed but remains opened.
   *
   * @param out
   *     the output stream where to store the configuration.
   * @throws ConfigurationError
   *     if any error occurs.
   */
  public synchronized void store(final OutputStream out) {
    LOGGER.debug("Storing XML configuration from {}", out);
    try {
      XmlSerialization.serialize(DefaultConfig.class, this, out);
    } catch (final XmlException e) {
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
    LOGGER.debug("Storing XML configuration from {}", out);
    try {
      XmlSerialization.serialize(DefaultConfig.class, this, out);
    } catch (final XmlException e) {
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
    LOGGER.debug("Storing XML configuration from {}", writer);
    try {
      XmlSerialization.serialize(DefaultConfig.class, this, writer);
    } catch (final XmlException e) {
      throw new ConfigurationError(e);
    }
  }

  /**
   * Stores this configuration to an XML file.
   *
   * @param file
   *     the XML file where to store the configuration.
   * @throws ConfigurationError
   *     if any error occurs.
   */
  public synchronized void store(final File file) {
    LOGGER.debug("Storing XML configuration from {}", file);
    try {
      XmlSerialization.serialize(DefaultConfig.class, this, file);
    } catch (final XmlException e) {
      throw new ConfigurationError(e);
    }
  }
}