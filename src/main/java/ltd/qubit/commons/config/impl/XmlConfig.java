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
 * 一个{@link Config}对象，用于从XML文件加载/存储配置。
 *
 * @author 胡海星
 */
@ThreadSafe
public final class XmlConfig extends DefaultConfig {

  @Serial
  private static final long serialVersionUID = -2988726634410349164L;

  /**
   * 构造一个空的{@link XmlConfig}。
   */
  public XmlConfig() {
  }

  /**
   * 构造一个{@link XmlConfig}。
   *
   * @param resource
   *     要加载配置的XML资源的路径。
   * @throws ConfigurationError
   *     如果发生任何错误。
   */
  public XmlConfig(final String resource) {
    load(resource);
  }

  /**
   * 构造一个{@link XmlConfig}。
   *
   * @param resource
   *     要加载配置的XML资源的路径。
   * @param loaderClass
   *     用于加载资源的类。
   * @throws ConfigurationError
   *     如果发生任何错误。
   */
  public XmlConfig(final String resource, final Class<?> loaderClass) {
    load(resource, loaderClass);
  }

  /**
   * 构造一个{@link XmlConfig}。
   *
   * @param resource
   *     要加载配置的XML资源的路径。
   * @param loader
   *     用于加载资源的类加载器。
   * @throws ConfigurationError
   *     如果发生任何错误。
   */
  public XmlConfig(final String resource, final ClassLoader loader) {
    load(resource, loader);
  }

  /**
   * 构造一个{@link XmlConfig}。
   *
   * @param url
   *     要加载配置的XML资源的URL。
   * @throws ConfigurationError
   *     如果发生任何错误。
   */
  public XmlConfig(final Url url) {
    load(url);
  }

  /**
   * 构造一个{@link XmlConfig}。
   *
   * @param url
   *     要加载配置的XML资源的URL。
   * @throws ConfigurationError
   *     如果发生任何错误。
   */
  public XmlConfig(final URL url) {
    load(url);
  }

  /**
   * 构造一个{@link XmlConfig}。
   *
   * @param uri
   *     要加载配置的XML资源的URI。
   * @throws ConfigurationError
   *     如果发生任何错误。
   */
  public XmlConfig(final URI uri) {
    load(uri);
  }

  /**
   * 构造一个{@link XmlConfig}。
   *
   * @param file
   *     要加载配置的XML文件。
   * @throws ConfigurationError
   *     如果发生任何错误。
   */
  public XmlConfig(final File file) {
    load(file);
  }

  /**
   * 构造一个{@link XmlConfig}。
   *
   * <p>调用此函数后，输入流保持打开状态。
   *
   * @param in
   *     存储要加载配置的XML文件的输入流。
   * @throws ConfigurationError
   *     如果发生任何错误。
   */
  public XmlConfig(final InputStream in) {
    load(in);
  }

  /**
   * 构造一个{@link XmlConfig}。
   *
   * <p>调用此函数后，读取器保持打开状态。
   *
   * @param reader
   *     存储要加载配置的XML文件的读取器。
   * @throws ConfigurationError
   *     如果发生任何错误。
   */
  public XmlConfig(final Reader reader) {
    load(reader);
  }

  /**
   * 从XML文件加载配置。
   *
   * @param resource
   *     要加载配置的XML资源的路径。
   * @throws ConfigurationError
   *     如果发生任何错误。
   */
  public synchronized void load(final String resource) {
    logger.debug("Loading XML configuration from {}", resource);
    try {
      final URL url = SystemUtils.getResource(resource);
      if (url == null) {
        throw new ConfigurationError("Cannot find resource: " + resource);
      }
      final DefaultConfig config = XmlSerialization.deserialize(DefaultConfig.class, url);
      properties = config.properties;
    } catch (final XmlException e) {
      throw new ConfigurationError(e);
    }
  }

  /**
   * 从XML文件加载配置。
   *
   * @param resource
   *     要加载配置的XML资源的路径。
   * @param loaderClass
   *     用于加载资源的类。
   * @throws ConfigurationError
   *     如果发生任何错误。
   */
  public synchronized void load(final String resource, final Class<?> loaderClass) {
    logger.debug("Loading XML configuration from {}", resource);
    try {
      final DefaultConfig config = XmlSerialization.deserialize(
          DefaultConfig.class, resource, loaderClass);
      properties = config.properties;
    } catch (final XmlException e) {
      throw new ConfigurationError(e);
    }
  }

  /**
   * 从XML文件加载配置。
   *
   * @param resource
   *     要加载配置的XML资源的路径。
   * @param loader
   *     用于加载资源的类加载器。
   * @throws ConfigurationError
   *     如果发生任何错误。
   */
  public synchronized void load(final String resource, final ClassLoader loader) {
    logger.debug("Loading XML configuration from {}", resource);
    try {
      final DefaultConfig config = XmlSerialization.deserialize(DefaultConfig.class, resource, loader);
      properties = config.properties;
    } catch (final XmlException e) {
      throw new ConfigurationError(e);
    }
  }

  /**
   * 从XML文件加载配置。
   *
   * @param url
   *     要加载配置的XML资源的URL。
   * @throws ConfigurationError
   *     如果发生任何错误。
   */
  public synchronized void load(final Url url) {
    logger.debug("Loading XML configuration from {}", url);
    try {
      final DefaultConfig config = XmlSerialization.deserialize(DefaultConfig.class, url);
      properties = config.properties;
    } catch (final XmlException e) {
      throw new ConfigurationError(e);
    }
  }

  /**
   * 从XML文件加载配置。
   *
   * @param url
   *     要加载配置的XML资源的URL。
   * @throws ConfigurationError
   *     如果发生任何错误。
   */
  public synchronized void load(final URL url) {
    logger.debug("Loading XML configuration from {}", url);
    try {
      final DefaultConfig config = XmlSerialization.deserialize(DefaultConfig.class, url);
      properties = config.properties;
    } catch (final XmlException e) {
      throw new ConfigurationError(e);
    }
  }

  /**
   * 从XML文件加载配置。
   *
   * @param uri
   *     要加载配置的XML资源的URI。
   * @throws ConfigurationError
   *     如果发生任何错误。
   */
  public synchronized void load(final URI uri) {
    logger.debug("Loading XML configuration from {}", uri);
    try {
      final DefaultConfig config = XmlSerialization.deserialize(DefaultConfig.class, uri);
      properties = config.properties;
    } catch (final XmlException e) {
      throw new ConfigurationError(e);
    }
  }

  /**
   * 从XML文件加载配置。
   *
   * @param file
   *     要加载配置的XML文件。
   * @throws ConfigurationError
   *     如果发生任何错误。
   */
  public synchronized void load(final File file) {
    logger.debug("Loading XML configuration from {}", file);
    try {
      final DefaultConfig config = XmlSerialization.deserialize(DefaultConfig.class, file);
      properties = config.properties;
    } catch (final XmlException e) {
      throw new ConfigurationError(e);
    }
  }

  /**
   * 从从输入流读取的XML加载配置。
   *
   * <p>调用此函数后，输入流保持打开状态。
   *
   * @param in
   *     要加载配置的XML文件的输入流。
   * @throws ConfigurationError
   *     如果发生任何错误。
   */
  public synchronized void load(final InputStream in) {
    logger.debug("Loading XML configuration from {}", in);
    try {
      final DefaultConfig config = XmlSerialization.deserialize(DefaultConfig.class, in);
      properties = config.properties;
    } catch (final XmlException e) {
      throw new ConfigurationError(e);
    }
  }

  /**
   * 从从读取器读取的XML加载配置。
   *
   * <p>调用此函数后，读取器保持打开状态。
   *
   * @param reader
   *     要加载配置的XML文件的读取器。
   * @throws ConfigurationError
   *     如果发生任何错误。
   */
  public synchronized void load(final Reader reader) {
    logger.debug("Loading XML configuration from {}", reader);
    try {
      final DefaultConfig config = XmlSerialization.deserialize(DefaultConfig.class, reader);
      properties = config.properties;
    } catch (final XmlException e) {
      throw new ConfigurationError(e);
    }
  }

  /**
   * 将此配置存储到输出流。
   *
   * <p>调用此函数后，流被刷新但保持打开状态。
   *
   * @param out
   *     要存储配置的输出流。
   * @throws ConfigurationError
   *     如果发生任何错误。
   */
  public synchronized void store(final OutputStream out) {
    logger.debug("Storing XML configuration from {}", out);
    try {
      XmlSerialization.serialize(DefaultConfig.class, this, out);
    } catch (final XmlException e) {
      throw new ConfigurationError(e);
    }
  }

  /**
   * 将此配置存储到打印流。
   *
   * <p>调用此函数后，流被刷新但保持打开状态。
   *
   * @param out
   *     要存储配置的打印流。
   * @throws ConfigurationError
   *     如果发生任何错误。
   */
  public synchronized void store(final PrintStream out) {
    logger.debug("Storing XML configuration from {}", out);
    try {
      XmlSerialization.serialize(DefaultConfig.class, this, out);
    } catch (final XmlException e) {
      throw new ConfigurationError(e);
    }
  }

  /**
   * 将此配置存储到写入器。
   *
   * <p>调用此函数后，写入器被刷新但保持打开状态。
   *
   * @param writer
   *     要存储配置的写入器。
   * @throws ConfigurationError
   *     如果发生任何错误。
   */
  public synchronized void store(final Writer writer) {
    logger.debug("Storing XML configuration from {}", writer);
    try {
      XmlSerialization.serialize(DefaultConfig.class, this, writer);
    } catch (final XmlException e) {
      throw new ConfigurationError(e);
    }
  }

  /**
   * 将此配置存储到XML文件。
   *
   * @param file
   *     要存储配置的XML文件。
   * @throws ConfigurationError
   *     如果发生任何错误。
   */
  public synchronized void store(final File file) {
    logger.debug("Storing XML configuration from {}", file);
    try {
      XmlSerialization.serialize(DefaultConfig.class, this, file);
    } catch (final XmlException e) {
      throw new ConfigurationError(e);
    }
  }
}