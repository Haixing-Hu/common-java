////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.io.resource;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

import javax.annotation.Nullable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * {@link Resource} 实现的便利基类，预实现典型行为。
 * <p>
 * "exists" 方法将检查是否可以打开 File 或 InputStream；
 * "isOpen" 将始终返回 false；
 * "getURL" 和 "getFile" 抛出异常；
 * "toString" 将返回描述。
 * <p>
 * 此类是 {@code org.springframework.core.io.AbstractResource} 的副本，
 * 略有修改。用于避免对 Spring Framework 的依赖。
 *
 * @author Juergen Hoeller
 * @author Sam Brannen
 * @author 胡海星
 */
public abstract class AbstractResource implements Resource {

  protected final Logger logger = LoggerFactory.getLogger(this.getClass());

  /**
   * 此实现检查是否可以打开 File，回退到检查是否可以打开 InputStream。
   * <p>
   * 这将涵盖目录和内容资源。
   */
  @Override
  public boolean exists() {
    // Try file existence: can we find the file in the file system?
    if (isFile()) {
      try {
        return getFile().exists();
      } catch (final IOException ex) {
        logger.debug("Could not retrieve File for existence check of {}",
            getDescription(), ex);
      }
    }
    // Fall back to stream existence: can we open the stream?
    try {
      getInputStream().close();
      return true;
    } catch (final Throwable ex) {
      logger.debug("Could not retrieve InputStream for existence check of {}",
          getDescription(), ex);
      return false;
    }
  }

  /**
   * 此实现对于 {@link #exists() 存在的} 资源始终返回 {@code true}（自 5.1 版本修订）。
   */
  @Override
  public boolean isReadable() {
    return exists();
  }

  /**
   * 此实现始终返回 {@code false}。
   */
  @Override
  public boolean isOpen() {
    return false;
  }

  /**
   * 此实现始终返回 {@code false}。
   */
  @Override
  public boolean isFile() {
    return false;
  }

  /**
   * 此实现抛出 FileNotFoundException，假设资源无法解析为 URL。
   */
  @Override
  public URL getURL() throws IOException {
    final String message = getDescription() + " cannot be resolved to URL";
    throw new FileNotFoundException(message);
  }

  /**
   * 此实现基于 {@link #getURL()} 返回的 URL 构建 URI。
   */
  @Override
  public URI getURI() throws IOException {
    final URL url = getURL();
    try {
      return ResourceUtils.toURI(url);
    } catch (final URISyntaxException ex) {
      throw new IOException("Invalid URI [" + url + "]", ex);
    }
  }

  /**
   * 此实现抛出 FileNotFoundException，假设资源无法解析为绝对文件路径。
   */
  @Override
  public File getFile() throws IOException {
    final String message = getDescription() + " cannot be resolved to absolute file path";
    throw new FileNotFoundException(message);
  }

  /**
   * 此实现使用 {@link #getInputStream()} 的结果返回 {@link Channels#newChannel(InputStream)}。
   * <p>
   * 这与 {@link org.springframework.core.io.Resource} 中相应的默认方法相同，
   * 但在此处镜像以实现类层次结构中高效的 JVM 级别调度。
   */
  @Override
  public ReadableByteChannel readableChannel() throws IOException {
    return Channels.newChannel(getInputStream());
  }

  /**
   * 此方法读取整个 InputStream 以确定内容长度。
   * <p>
   * 对于 {@code InputStreamResource} 的自定义子类，我们强烈建议
   * 使用更优化的实现重写此方法，例如检查文件长度，
   * 或者如果流只能读取一次，则可能简单地返回 -1。
   *
   * @see #getInputStream()
   */
  @Override
  public long contentLength() throws IOException {
    final InputStream is = getInputStream();
    try {
      long size = 0;
      final byte[] buf = new byte[256];
      int read;
      while ((read = is.read(buf)) != -1) {
        size += read;
      }
      return size;
    } finally {
      try {
        is.close();
      } catch (final IOException ex) {
        logger.debug("Could not close content-length InputStream for {}",
            getDescription(), ex);
      }
    }
  }

  /**
   * 此实现检查底层文件的时间戳（如果可用）。
   *
   * @see #getFileForLastModifiedCheck()
   */
  @Override
  public long lastModified() throws IOException {
    final File fileToCheck = getFileForLastModifiedCheck();
    final long lastModified = fileToCheck.lastModified();
    if (lastModified == 0L && !fileToCheck.exists()) {
      final String message = getDescription()
          + " cannot be resolved in the file system for checking its"
          + " last-modified timestamp";
      throw new FileNotFoundException(message);
    }
    return lastModified;
  }

  /**
   * 确定用于时间戳检查的文件。
   * <p>
   * 默认实现委托给 {@link #getFile()}。
   *
   * @return 用于时间戳检查的文件（永远不为 {@code null}）
   * @throws FileNotFoundException
   *     如果资源无法解析为绝对文件路径，即在文件系统中不可用
   * @throws IOException
   *     如果发生一般解析/读取失败
   */
  protected File getFileForLastModifiedCheck() throws IOException {
    return getFile();
  }

  /**
   * 此实现抛出 FileNotFoundException，假设无法为此资源创建相对资源。
   */
  @Override
  public Resource createRelative(final String relativePath) throws IOException {
    final String message = "Cannot create a relative resource for " + getDescription();
    throw new FileNotFoundException(message);
  }

  /**
   * 此实现始终返回 {@code null}，假设此资源类型没有文件名。
   */
  @Override
  @Nullable
  public String getFilename() {
    return null;
  }

  /**
   * 此实现比较描述字符串。
   *
   * @see #getDescription()
   */
  @Override
  public boolean equals(@Nullable final Object other) {
    if (this == other) {
      return true;
    }
    if (!(other instanceof Resource)) {
      return false;
    }
    final Resource that = (Resource) other;
    return getDescription().equals(that.getDescription());
  }

  /**
   * 此实现返回描述的哈希码。
   *
   * @see #getDescription()
   */
  @Override
  public int hashCode() {
    return getDescription().hashCode();
  }

  /**
   * 此实现返回此资源的描述。
   *
   * @see #getDescription()
   */
  @Override
  public String toString() {
    return getDescription();
  }

}