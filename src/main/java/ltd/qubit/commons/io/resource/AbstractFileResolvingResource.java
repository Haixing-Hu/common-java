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
 * Abstract base class for resources which resolve URLs into File references,
 * such as {@link UrlResource} or {@link ClassPathResource}.
 * <p>
 * Detects the "file" protocol as well as the JBoss "vfs" protocol in URLs,
 * resolving file system references accordingly.
 * <p>
 * This class is a copy of {@code org.springframework.core.io.AbstractFileResolvingResource}
 * with slight modifications. It is used to avoid the dependency of Spring Framework.
 *
 * @author Juergen Hoeller
 * @author Haixing Hu
 */
public abstract class AbstractFileResolvingResource extends AbstractResource {

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

  @Override
  public boolean isReadable() {
    try {
      return checkReadable(getURL());
    } catch (final IOException ex) {
      return false;
    }
  }

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
   * This implementation returns a File reference for the underlying class path
   * resource, provided that it refers to a file in the file system.
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
   * This implementation determines the underlying File (or jar file, in case of
   * a resource in a jar/zip).
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
   * Determine whether the given {@link URI} represents a file in a file
   * system.
   *
   * @see #getFile(URI)
   * @since 5.0
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
   * This implementation returns a File reference for the given URI-identified
   * resource, provided that it refers to a file in the file system.
   *
   * @see ResourceUtils#getFile(java.net.URI, String)
   */
  protected File getFile(final URI uri) throws IOException {
    if (uri.getScheme().startsWith(ResourceUtils.URL_PROTOCOL_VFS)) {
      return VfsResourceDelegate.getResource(uri).getFile();
    }
    return ResourceUtils.getFile(uri, getDescription());
  }

  /**
   * This implementation returns a FileChannel for the given URI-identified
   * resource, provided that it refers to a file in the file system.
   *
   * @see #getFile()
   * @since 5.0
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
   * Customize the given {@link URLConnection} before fetching the resource.
   * <p>Calls
   * {@link
   * ResourceUtils#useCachesIfNecessary(URLConnection)}
   * and
   * delegates to {@link #customizeConnection(HttpURLConnection)} if possible.
   * Can be overridden in subclasses.
   *
   * @param con
   *     the URLConnection to customize
   * @throws IOException
   *     if thrown from URLConnection methods
   */
  protected void customizeConnection(final URLConnection con) throws IOException {
    ResourceUtils.useCachesIfNecessary(con);
    if (con instanceof HttpURLConnection) {
      final HttpURLConnection httpConn = (HttpURLConnection) con;
      customizeConnection(httpConn);
    }
  }

  /**
   * Customize the given {@link HttpURLConnection} before fetching the
   * resource.
   * <p>Can be overridden in subclasses for configuring request headers and
   * timeouts.
   *
   * @param con
   *     the HttpURLConnection to customize
   * @throws IOException
   *     if thrown from HttpURLConnection methods
   */
  protected void customizeConnection(final HttpURLConnection con) throws IOException {
  }

  /**
   * Inner delegate class, avoiding a hard JBoss VFS API dependency at runtime.
   */
  private static class VfsResourceDelegate {

    public static Resource getResource(final URL url) throws IOException {
      return new VfsResource(VfsUtils.getRoot(url));
    }

    public static Resource getResource(final URI uri) throws IOException {
      return new VfsResource(VfsUtils.getRoot(uri));
    }
  }
}