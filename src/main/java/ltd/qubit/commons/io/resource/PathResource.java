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
import java.io.OutputStream;
import java.net.URI;
import java.net.URL;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.NoSuchFileException;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.nio.file.spi.FileSystemProvider;

import javax.annotation.Nonnull;

import ltd.qubit.commons.lang.Equality;
import ltd.qubit.commons.lang.Hash;
import ltd.qubit.commons.text.tostring.ToStringBuilder;

/**
 * {@link Resource} 的 {@link Path} 句柄实现，通过 {@code Path} API 执行所有
 * 操作和转换。支持解析为 {@link File} 和 {@link URL}。实现了扩展的
 * {@link WritableResource} 接口。
 * <p>
 * 注意：从 5.1 开始，{@link Path} 支持在
 * {@link FileSystemResource#FileSystemResource(Path) FileSystemResource}
 * 中也可用，它应用 Spring 的标准基于字符串的路径转换，但通过 {@link Files} API
 * 执行所有操作。此 {@code PathResource} 实际上是一个纯粹的基于
 * {@code java.nio.path.Path} 的替代方案，具有不同的 {@code createRelative} 行为。
 * <p>
 * 此类是 {@code org.springframework.core.io.PathResource} 的副本，经过少量修改。
 * 它用于避免对 Spring Framework 的依赖。
 *
 * @author 胡海星
 * @see Path
 * @see Files
 * @see FileSystemResource
 */
public class PathResource extends AbstractResource implements WritableResource {

  private final Path path;

  /**
   * 从 {@link Path} 句柄创建一个新的 {@code PathResource}。
   * <p>
   * 注意：与 {@link FileSystemResource} 不同，当通过 {@link #createRelative}
   * 构建相对资源时，相对路径将在给定的根路径<i>下</i>构建：例如，
   * Paths.get("C:/dir1/")，相对路径 "dir2" → "C:/dir1/dir2"！
   *
   * @param path
   *     一个 {@link Path} 句柄。
   */
  public PathResource(final Path path) {
    if (path == null) {
      throw new IllegalArgumentException("Path must not be null");
    }
    this.path = path.normalize();
  }

  /**
   * 从路径字符串创建一个新的 {@code PathResource}。
   * <p>
   * 注意：与 {@link FileSystemResource} 不同，当通过 {@link #createRelative}
   * 构建相对资源时，相对路径将在给定的根路径<i>下</i>构建：例如，
   * Paths.get("C:/dir1/")，相对路径 "dir2" → "C:/dir1/dir2"！
   *
   * @param path
   *     一个路径字符串。
   * @see Paths#get(String, String...)
   */
  public PathResource(final String path) {
    if (path == null) {
      throw new IllegalArgumentException("Path must not be null");
    }
    this.path = Paths.get(path).normalize();
  }

  /**
   * 从 {@link URI} 创建一个新的 {@code PathResource}。
   * <p>
   * 注意：与 {@link FileSystemResource} 不同，当通过 {@link #createRelative}
   * 构建相对资源时，相对路径将在给定的根路径<i>下</i>构建：例如，
   * Paths.get("C:/dir1/")，相对路径 "dir2" → "C:/dir1/dir2"！
   *
   * @param uri
   *     一个路径 URI。
   * @see Paths#get(URI)
   */
  public PathResource(final URI uri) {
    if (uri == null) {
      throw new IllegalArgumentException("URI must not be null");
    }
    path = Paths.get(uri).normalize();
  }


  /**
   * 返回此资源的路径。
   *
   * @return 此资源的路径。
   */
  public final String getPath() {
    return path.toString();
  }

  /**
   * 此实现返回底层文件是否存在。
   *
   * @see Files#exists(Path, LinkOption...)
   */
  @Override
  public boolean exists() {
    return Files.exists(path);
  }

  /**
   * 此实现检查底层文件是否标记为可读（并且对应于具有内容的实际文件，而不是目录）。
   *
   * @see Files#isReadable(Path)
   * @see Files#isDirectory(Path, LinkOption...)
   */
  @Override
  public boolean isReadable() {
    return (Files.isReadable(path) && !Files.isDirectory(path));
  }

  /**
   * 此实现为底层文件打开一个 {@link InputStream}。
   *
   * @see FileSystemProvider#newInputStream(Path, OpenOption...)
   */
  @Nonnull
  @Override
  public InputStream getInputStream() throws IOException {
    if (!exists()) {
      throw new FileNotFoundException(getPath() + " (no such file or directory)");
    }
    if (Files.isDirectory(path)) {
      throw new FileNotFoundException(getPath() + " (is a directory)");
    }
    return Files.newInputStream(path);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public byte[] getContentAsByteArray() throws IOException {
    try {
      return Files.readAllBytes(path);
    } catch (final NoSuchFileException ex) {
      throw new FileNotFoundException(ex.getMessage());
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getContentAsString(final Charset charset) throws IOException {
    try {
      return Files.readString(path, charset);
    } catch (final NoSuchFileException ex) {
      throw new FileNotFoundException(ex.getMessage());
    }
  }

  /**
   * 此实现检查底层文件是否标记为可写（并且对应于具有内容的实际文件，而不是目录）。
   *
   * @see Files#isWritable(Path)
   * @see Files#isDirectory(Path, LinkOption...)
   */
  @Override
  public boolean isWritable() {
    return (Files.isWritable(path) && !Files.isDirectory(path));
  }

  /**
   * 此实现为底层文件打开一个 {@link OutputStream}。
   *
   * @see FileSystemProvider#newOutputStream(Path, OpenOption...)
   */
  @Override
  public OutputStream getOutputStream() throws IOException {
    if (Files.isDirectory(path)) {
      throw new FileNotFoundException(getPath() + " (is a directory)");
    }
    return Files.newOutputStream(path);
  }

  /**
   * 此实现为底层文件返回一个 {@link URL}。
   *
   * @see Path#toUri()
   * @see URI#toURL()
   */
  @Override
  public URL getURL() throws IOException {
    return path.toUri().toURL();
  }

  /**
   * 此实现为底层文件返回一个 {@link URI}。
   *
   * @see Path#toUri()
   */
  @Override
  public URI getURI() throws IOException {
    return path.toUri();
  }

  /**
   * 此实现始终指示一个文件。
   */
  @Override
  public boolean isFile() {
    return true;
  }

  /**
   * 此实现返回底层的 {@link File} 引用。
   */
  @Override
  public File getFile() throws IOException {
    try {
      return path.toFile();
    } catch (final UnsupportedOperationException ex) {
      // Only paths on the default file system can be converted to a File:
      // Do exception translation for cases where conversion is not possible.
      throw new FileNotFoundException(path + " cannot be resolved to absolute file path");
    }
  }

  /**
   * 此实现为底层文件打开一个 {@link ReadableByteChannel}。
   *
   * @see Files#newByteChannel(Path, OpenOption...)
   */
  @Override
  public ReadableByteChannel readableChannel() throws IOException {
    try {
      return Files.newByteChannel(this.path, StandardOpenOption.READ);
    } catch (final NoSuchFileException ex) {
      throw new FileNotFoundException(ex.getMessage());
    }
  }

  /**
   * 此实现为底层文件打开一个 {@link WritableByteChannel}。
   *
   * @see Files#newByteChannel(Path, OpenOption...)
   */
  @Override
  public WritableByteChannel writableChannel() throws IOException {
    return Files.newByteChannel(this.path, StandardOpenOption.WRITE);
  }

  /**
   * 此实现返回底层文件的长度。
   */
  @Override
  public long contentLength() throws IOException {
    return Files.size(this.path);
  }

  /**
   * 此实现返回底层文件的时间戳。
   *
   * @see Files#getLastModifiedTime(Path, LinkOption...)
   */
  @Override
  public long lastModified() throws IOException {
    // We can not use the superclass method since it uses conversion to a File and
    // only a Path on the default file system can be converted to a File...
    return Files.getLastModifiedTime(this.path).toMillis();
  }

  /**
   * 此实现创建一个 {@link PathResource}，将给定的路径应用于此资源描述符的底层
   * 文件路径的相对路径。
   *
   * @see Path#resolve(String)
   */
  @Override
  public Resource createRelative(final String relativePath) {
    return new PathResource(path.resolve(relativePath));
  }

  /**
   * 此实现返回文件名。
   *
   * @see Path#getFileName()
   */
  @Override
  public String getFilename() {
    return path.getFileName().toString();
  }

  @Override
  public String getDescription() {
    return "path [" + path.toAbsolutePath() + "]";
  }

  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if ((o == null) || (getClass() != o.getClass())) {
      return false;
    }
    final PathResource other = (PathResource) o;
    return Equality.equals(path, other.path);
  }

  public int hashCode() {
    final int multiplier = 7;
    int result = 3;
    result = Hash.combine(result, multiplier, path);
    return result;
  }

  public String toString() {
    return new ToStringBuilder(this)
        .appendSuper(super.toString())
        .append("path", path)
        .toString();
  }
}