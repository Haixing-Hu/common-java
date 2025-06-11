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
import java.net.HttpURLConnection;
import java.net.JarURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.NoSuchFileException;
import java.nio.file.StandardOpenOption;
import java.util.jar.JarEntry;

import ltd.qubit.commons.io.VfsUtils;

/**
 * 将 URL 解析为 File 引用的资源的抽象基类，
 * 例如 {@link UrlResource} 或 {@link ClassPathResource}。
 * <p>
 * 检测 URL 中的 "file" 协议以及 JBoss "vfs" 协议，
 * 相应地解析文件系统引用。
 * <p>
 * 此类是 {@code org.springframework.core.io.AbstractFileResolvingResource} 的副本，
 * 略有修改。用于避免对 Spring Framework 的依赖。
 *
 * @author Juergen Hoeller
 * @author 胡海星
 */
public abstract class AbstractFileResolvingResource extends AbstractResource {

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean exists() {
    try {
      final URL url = getURL();
      if (ResourceUtils.isFileURL(url)) {
        // Proceed with file system resolution
        return getFile().exists();
      } else {
        // Try a URL connection content-length header
        final URLConnection con = url.openConnection();
        customizeConnection(con);
        final HttpURLConnection httpCon;
        if (con instanceof HttpURLConnection) {
          httpCon = (HttpURLConnection) con;
        } else {
          httpCon = null;
        }
        if (httpCon != null) {
          httpCon.setRequestMethod("HEAD");
          final int code = httpCon.getResponseCode();
          if (code == HttpURLConnection.HTTP_OK) {
            return true;
          } else if (code == HttpURLConnection.HTTP_NOT_FOUND) {
            return false;
          }
        }
        if (con.getContentLengthLong() > 0) {
          return true;
        }
        if (httpCon != null) {
          // No HTTP OK status, and no content-length header: give up
          httpCon.disconnect();
          return false;
        } else {
          // Fall back to stream existence: can we open the stream?
          getInputStream().close();
          return true;
        }
      }
    } catch (final IOException ex) {
      return false;
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isReadable() {
    try {
      return checkReadable(getURL());
    } catch (final IOException ex) {
      return false;
    }
  }

  /**
   * 检查给定URL是否可读。
   *
   * @param url 要检查的URL。
   * @return 如果可读则返回true，否则返回false。
   */
  boolean checkReadable(final URL url) {
    try {
      if (ResourceUtils.isFileURL(url)) {
        // Proceed with file system resolution
        final File file = getFile();
        return (file.canRead() && !file.isDirectory());
      } else {
        // Try InputStream resolution for jar resources
        final URLConnection con = url.openConnection();
        customizeConnection(con);
        if (con instanceof HttpURLConnection) {
          final HttpURLConnection httpCon = (HttpURLConnection) con;
          httpCon.setRequestMethod("HEAD");
          final int code = httpCon.getResponseCode();
          if (code != HttpURLConnection.HTTP_OK) {
            httpCon.disconnect();
            return false;
          }
        } else if (con instanceof JarURLConnection) {
          final JarURLConnection jarCon = (JarURLConnection) con;
          final JarEntry jarEntry = jarCon.getJarEntry();
          if (jarEntry == null) {
            return false;
          } else {
            return !jarEntry.isDirectory();
          }
        }
        final long contentLength = con.getContentLengthLong();
        if (contentLength > 0) {
          return true;
        } else if (contentLength == 0) {
          // Empty file or directory -> not considered readable...
          return false;
        } else {
          // Fall back to stream existence: can we open the stream?
          getInputStream().close();
          return true;
        }
      }
    } catch (final IOException ex) {
      return false;
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isFile() {
    try {
      final URL url = getURL();
      if (url.getProtocol().startsWith(ResourceUtils.URL_PROTOCOL_VFS)) {
        return VfsResourceDelegate.getResource(url).isFile();
      }
      return ResourceUtils.URL_PROTOCOL_FILE.equals(url.getProtocol());
    } catch (final IOException ex) {
      return false;
    }
  }

  /**
   * 此实现为底层类路径资源返回 File 引用，
   * 前提是它引用文件系统中的文件。
   *
   * @see ResourceUtils#getFile(java.net.URL, String)
   */
  @Override
  public File getFile() throws IOException {
    final URL url = getURL();
    if (url.getProtocol().startsWith(ResourceUtils.URL_PROTOCOL_VFS)) {
      return VfsResourceDelegate.getResource(url).getFile();
    }
    return ResourceUtils.getFile(url, getDescription());
  }

  /**
   * 此实现确定底层文件（或在 jar/zip 中的资源情况下的 jar 文件）。
   */
  @Override
  protected File getFileForLastModifiedCheck() throws IOException {
    final URL url = getURL();
    if (ResourceUtils.isJarURL(url)) {
      final URL actualUrl = ResourceUtils.extractArchiveURL(url);
      if (actualUrl.getProtocol().startsWith(ResourceUtils.URL_PROTOCOL_VFS)) {
        return VfsResourceDelegate.getResource(actualUrl).getFile();
      }
      return ResourceUtils.getFile(actualUrl, "Jar URL");
    } else {
      return getFile();
    }
  }

  /**
   * 确定给定的 {@link URI} 是否表示文件系统中的文件。
   *
   * @param uri 要检查的URI。
   * @return 如果是文件则返回true，否则返回false。
   * @see #getFile(URI)
   */
  protected boolean isFile(final URI uri) {
    try {
      if (uri.getScheme().startsWith(ResourceUtils.URL_PROTOCOL_VFS)) {
        return VfsResourceDelegate.getResource(uri).isFile();
      }
      return ResourceUtils.URL_PROTOCOL_FILE.equals(uri.getScheme());
    } catch (final IOException ex) {
      return false;
    }
  }

  /**
   * 此实现为给定的 URI 标识的资源返回 File 引用，
   * 前提是它引用文件系统中的文件。
   *
   * @param uri 要转换为文件的URI。
   * @return 对应的文件对象。
   * @throws IOException 如果发生I/O错误。
   * @see ResourceUtils#getFile(java.net.URI, String)
   */
  protected File getFile(final URI uri) throws IOException {
    if (uri.getScheme().startsWith(ResourceUtils.URL_PROTOCOL_VFS)) {
      return VfsResourceDelegate.getResource(uri).getFile();
    }
    return ResourceUtils.getFile(uri, getDescription());
  }

  /**
   * 此实现为给定的 URI 标识的资源返回 FileChannel，
   * 前提是它引用文件系统中的文件。
   *
   * @return 可读字节通道。
   * @throws IOException 如果发生I/O错误。
   * @see #getFile()
   */
  @Override
  public ReadableByteChannel readableChannel() throws IOException {
    try {
      // Try file system channel
      return FileChannel.open(getFile().toPath(), StandardOpenOption.READ);
    } catch (final FileNotFoundException | NoSuchFileException ex) {
      // Fall back to InputStream adaptation in superclass
      return super.readableChannel();
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public long contentLength() throws IOException {
    final URL url = getURL();
    if (ResourceUtils.isFileURL(url)) {
      // Proceed with file system resolution
      final File file = getFile();
      final long length = file.length();
      if (length == 0L && !file.exists()) {
        throw new FileNotFoundException(getDescription()
            + " cannot be resolved in the file system for checking its content length");
      }
      return length;
    } else {
      // Try a URL connection content-length header
      final URLConnection con = url.openConnection();
      customizeConnection(con);
      if (con instanceof HttpURLConnection) {
        final HttpURLConnection httpCon = (HttpURLConnection) con;
        httpCon.setRequestMethod("HEAD");
      }
      return con.getContentLengthLong();
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public long lastModified() throws IOException {
    final URL url = getURL();
    boolean fileCheck = false;
    if (ResourceUtils.isFileURL(url)
        || ResourceUtils.isJarURL(url)) {
      // Proceed with file system resolution
      fileCheck = true;
      try {
        final File fileToCheck = getFileForLastModifiedCheck();
        final long lastModified = fileToCheck.lastModified();
        if (lastModified > 0L || fileToCheck.exists()) {
          return lastModified;
        }
      } catch (final FileNotFoundException ex) {
        // Defensively fall back to URL connection check instead
      }
    }
    // Try a URL connection last-modified header
    final URLConnection con = url.openConnection();
    customizeConnection(con);
    if (con instanceof HttpURLConnection) {
      final HttpURLConnection httpCon = (HttpURLConnection) con;
      httpCon.setRequestMethod("HEAD");
    }
    final long lastModified = con.getLastModified();
    if (fileCheck && lastModified == 0 && con.getContentLengthLong() <= 0) {
      throw new FileNotFoundException(getDescription()
          + " cannot be resolved in the file system for checking its last-modified timestamp");
    }
    return lastModified;
  }

  /**
   * 在获取资源之前自定义给定的 {@link URLConnection}。
   * <p>调用 {@link ResourceUtils#useCachesIfNecessary(URLConnection)}
   * 并在可能的情况下委托给 {@link #customizeConnection(HttpURLConnection)}。
   * 可以在子类中重写。
   *
   * @param con 要自定义的 URLConnection。
   * @throws IOException 如果从 URLConnection 方法抛出。
   */
  protected void customizeConnection(final URLConnection con) throws IOException {
    ResourceUtils.useCachesIfNecessary(con);
    if (con instanceof HttpURLConnection) {
      final HttpURLConnection httpConn = (HttpURLConnection) con;
      customizeConnection(httpConn);
    }
  }

  /**
   * 在获取资源之前自定义给定的 {@link HttpURLConnection}。
   * <p>可以在子类中重写以配置请求头和超时。
   *
   * @param con 要自定义的 HttpURLConnection。
   * @throws IOException 如果从 HttpURLConnection 方法抛出。
   */
  protected void customizeConnection(final HttpURLConnection con) throws IOException {
  }

  /**
   * 内部委托类，避免在运行时对 JBoss VFS API 的硬依赖。
   */
  private static class VfsResourceDelegate {

    /**
     * 从URL获取VFS资源。
     *
     * @param url 要处理的URL。
     * @return VFS资源。
     * @throws IOException 如果发生I/O错误。
     */
    public static Resource getResource(final URL url) throws IOException {
      return new VfsResource(VfsUtils.getRoot(url));
    }

    /**
     * 从URI获取VFS资源。
     *
     * @param uri 要处理的URI。
     * @return VFS资源。
     * @throws IOException 如果发生I/O错误。
     */
    public static Resource getResource(final URI uri) throws IOException {
      return new VfsResource(VfsUtils.getRoot(uri));
    }
  }
}