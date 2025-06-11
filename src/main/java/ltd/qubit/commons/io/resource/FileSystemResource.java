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
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;
import java.nio.charset.Charset;
import java.nio.file.FileSystem;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.NoSuchFileException;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import ltd.qubit.commons.lang.Equality;
import ltd.qubit.commons.text.tostring.ToStringBuilder;

import static ltd.qubit.commons.io.resource.ResourceUtils.URL_PROTOCOL_FILE;
import static ltd.qubit.commons.io.resource.ResourceUtils.applyRelativePath;
import static ltd.qubit.commons.io.resource.ResourceUtils.cleanPath;

/**
 * {@link Resource} 的实现，用于处理文件系统中的 {@code java.io.File} 和
 * {@code java.nio.file.Path} 目标。支持解析为 {@code File} 和 {@code URL}。
 * 实现了扩展的 {@link WritableResource} 接口。
 * <p>
 * 注意：从 Spring Framework 5.0 开始，此 {@link Resource} 实现使用 NIO.2 API
 * 进行读/写交互。从 5.1 开始，它可以使用 {@link Path} 句柄构造，在这种情况下，
 * 它将通过 NIO.2 执行所有文件系统交互，仅在 {@link #getFile()} 时才使用 {@link File}。
 * <p>
 * 此类是 {@code org.springframework.core.io.FileSystemResource} 的一个副本，
 * 经过了少量修改。它的目的是为了避免对 Spring Framework 的依赖。
 *
 * @author 胡海星
 * @see #FileSystemResource(String)
 * @see #FileSystemResource(File)
 * @see #FileSystemResource(Path)
 * @see File
 * @see Files
 */
public class FileSystemResource extends AbstractResource implements WritableResource {

  private final String path;

  @Nullable
  private final File file;

  private final Path filePath;

  /**
   * 根据文件路径创建一个新的 {@code FileSystemResource}。
   * <p>
   * 注意：当通过 {@link #createRelative} 构建相对资源时，此处指定的资源基本路径是否以
   * 斜杠结尾会有所不同。在 "C:/dir1/" 的情况下，相对路径将在此根目录下构建：例如，
   * 相对路径 "dir2" → "C:/dir1/dir2"。在 "C:/dir1" 的情况下，相对路径将在同一目
   * 录级别应用：相对路径 "dir2" → "C:/dir2"。
   *
   * @param path
   *     文件路径。
   * @see #FileSystemResource(Path)
   */
  public FileSystemResource(final String path) {
    if (path == null) {
      throw new IllegalArgumentException("Path must not be null");
    }
    this.path = cleanPath(path);
    this.file = new File(path);
    this.filePath = this.file.toPath();
  }

  /**
   * 根据 {@link File} 句柄创建一个新的 {@code FileSystemResource}。
   * <p>
   * 注意：当通过 {@link #createRelative} 构建相对资源时，相对路径将应用于<i>同一
   * 目录级别</i>：例如，new File("C:/dir1")，相对路径 "dir2" → "C:/dir2"！
   * 如果您希望在给定的根目录下构建相对路径，请使用带有文件路径的
   * {@link #FileSystemResource(String) 构造函数}，并将结尾斜杠附加到根路径：
   * "C:/dir1/"，这表示此目录是所有相对路径的根。
   *
   * @param file
   *     一个 {@link File} 句柄。
   * @see #FileSystemResource(Path)
   * @see #getFile()
   */
  public FileSystemResource(final File file) {
    if (file == null) {
      throw new IllegalArgumentException("File must not be null");
    }
    this.path = cleanPath(file.getPath());
    this.file = file;
    this.filePath = file.toPath();
  }

  /**
   * 根据 {@link Path} 句柄创建一个新的 {@code FileSystemResource}，通过 NIO.2
   * 而不是 {@link File} 执行所有文件系统交互。
   * <p>
   * 与 {@link PathResource} 相反，此变体严格遵循通用的
   * {@link FileSystemResource} 约定，特别是在路径清理和
   * {@link #createRelative(String)} 处理方面。
   * <p>
   * 注意：当通过 {@link #createRelative} 构建相对资源时，相对路径将应用于
   * <i>同一目录级别</i>：例如，Paths.get("C:/dir1")，相对路径 "dir2" →
   * "C:/dir2"！如果您希望在给定的根目录下构建相对路径，请使用带有文件路径的
   * {@link #FileSystemResource(String) 构造函数}，并将结尾斜杠附加到根路径：
   * "C:/dir1/"，这表示此目录是所有相对路径的根。或者，考虑使用
   * {@link PathResource#PathResource(Path)} 进行 {@code java.nio.path.Path}
   * 的解析，以便在 {@code createRelative} 中总是嵌套相对路径。
   *
   * @param filePath
   *     指向文件的 {@link Path} 句柄。
   * @see #FileSystemResource(File)
   */
  public FileSystemResource(final Path filePath) {
    if (filePath == null) {
      throw new IllegalArgumentException("Path must not be null");
    }
    this.path = cleanPath(filePath.toString());
    this.file = null;
    this.filePath = filePath;
  }

  /**
   * 根据 {@link FileSystem} 句柄创建一个新的 {@code FileSystemResource}，
   * 用于定位指定的路径。
   * <p>
   * 这是 {@link #FileSystemResource(String)} 的替代方案，它通过 NIO.2 而不是
   * {@link File} 执行所有文件系统交互。
   *
   * @param fileSystem
   *     用于定位路径的 {@link FileSystem}。
   * @param path
   *     文件路径。
   * @see #FileSystemResource(File)
   */
  public FileSystemResource(final FileSystem fileSystem, final String path) {
    if (fileSystem == null) {
      throw new IllegalArgumentException("FileSystem must not be null");
    }
    if (path == null) {
      throw new IllegalArgumentException("Path must not be null");
    }
    this.path = cleanPath(path);
    this.file = null;
    this.filePath = fileSystem.getPath(this.path).normalize();
  }

  /**
   * 返回此资源的路径。
   *
   * @return 此资源的路径。
   */
  public final String getPath() {
    return this.path;
  }

  /**
   * 此实现返回底层文件是否存在。
   *
   * @see File#exists()
   * @see Files#exists(Path, LinkOption...)
   */
  @Override
  public boolean exists() {
    return (this.file != null ? this.file.exists() : Files.exists(this.filePath));
  }

  /**
   * 此实现检查底层文件是否标记为可读（并且对应于具有内容的实际文件，而不是目录）。
   *
   * @see File#canRead()
   * @see File#isDirectory()
   * @see Files#isReadable(Path)
   * @see Files#isDirectory(Path, LinkOption...)
   */
  @Override
  public boolean isReadable() {
    if (file != null) {
      return file.canRead() && !file.isDirectory();
    } else {
      return Files.isReadable(this.filePath) && !Files.isDirectory(this.filePath);
    }
  }

  /**
   * 此实现为底层文件打开一个 NIO 文件流。
   *
   * @see Files#newInputStream(Path, OpenOption...)
   */
  @Nonnull
  @Override
  public InputStream getInputStream() throws IOException {
    try {
      return Files.newInputStream(this.filePath);
    } catch (final NoSuchFileException ex) {
      throw new FileNotFoundException(ex.getMessage());
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public byte[] getContentAsByteArray() throws IOException {
    try {
      return Files.readAllBytes(this.filePath);
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
      return Files.readString(this.filePath, charset);
    } catch (final NoSuchFileException ex) {
      throw new FileNotFoundException(ex.getMessage());
    }
  }

  /**
   * 此实现检查底层文件是否标记为可写（并且对应于实际文件，而不是目录）。
   *
   * @see File#canWrite()
   * @see File#isDirectory()
   * @see Files#isWritable(Path)
   * @see Files#isDirectory(Path, LinkOption...)
   */
  @Override
  public boolean isWritable() {
    if (file != null) {
      return file.canWrite() && !file.isDirectory();
    } else {
      return Files.isWritable(this.filePath) && !Files.isDirectory(this.filePath);
    }
  }

  /**
   * 此实现为底层文件打开一个 {@code FileOutputStream}。
   *
   * @see Files#newOutputStream(Path, OpenOption...)
   */
  @Override
  public OutputStream getOutputStream() throws IOException {
    return Files.newOutputStream(this.filePath);
  }

  /**
   * 此实现返回底层文件的 URL。
   *
   * @see File#toURI()
   * @see Path#toUri()
   */
  @Override
  public URL getURL() throws IOException {
    if (file != null) {
      return file.toURI().toURL();
    } else {
      return filePath.toUri().toURL();
    }
  }

  /**
   * 此实现返回底层文件的 URI。
   *
   * @see File#toURI()
   * @see Path#toUri()
   */
  @Override
  public URI getURI() throws IOException {
    if (file != null) {
      return file.toURI();
    } else {
      URI uri = filePath.toUri();
      // Normalize URI? See https://github.com/spring-projects/spring-framework/issues/29275
      final String scheme = uri.getScheme();
      if (URL_PROTOCOL_FILE.equals(scheme)) {
        try {
          uri = new URI(scheme, uri.getPath(), null);
        } catch (final URISyntaxException ex) {
          throw new IOException("Failed to normalize URI: " + uri, ex);
        }
      }
      return uri;
    }
  }

  /**
   * 此实现始终指示一个文件。
   */
  @Override
  public boolean isFile() {
    return true;
  }

  /**
   * 此实现返回底层的 {@code File} 引用。
   */
  @Override
  public File getFile() {
    return (file != null ? file : filePath.toFile());
  }

  /**
   * 此实现为底层文件打开一个 {@code FileChannel}。
   *
   * @see FileChannel
   */
  @Override
  public ReadableByteChannel readableChannel() throws IOException {
    try {
      return FileChannel.open(filePath, StandardOpenOption.READ);
    } catch (final NoSuchFileException ex) {
      throw new FileNotFoundException(ex.getMessage());
    }
  }

  /**
   * 此实现为底层文件打开一个 {@code FileChannel}。
   *
   * @see FileChannel
   */
  @Override
  public WritableByteChannel writableChannel() throws IOException {
    return FileChannel.open(filePath, StandardOpenOption.WRITE);
  }

  /**
   * 此实现返回底层文件/路径的长度。
   */
  @Override
  public long contentLength() throws IOException {
    if (file != null) {
      final long length = file.length();
      if (length == 0L && !file.exists()) {
        throw new FileNotFoundException(getDescription()
            + " cannot be resolved in the file system for checking its content length");
      }
      return length;
    } else {
      try {
        return Files.size(filePath);
      } catch (final NoSuchFileException ex) {
        throw new FileNotFoundException(ex.getMessage());
      }
    }
  }

  /**
   * 此实现返回底层文件/路径的最后修改时间。
   */
  @Override
  public long lastModified() throws IOException {
    if (file != null) {
      return super.lastModified();
    } else {
      try {
        return Files.getLastModifiedTime(filePath).toMillis();
      } catch (final NoSuchFileException ex) {
        throw new FileNotFoundException(ex.getMessage());
      }
    }
  }

  /**
   * 此实现创建一个 {@code FileSystemResource}，将给定的路径应用于此资源描述符
   * 的底层文件路径的相对路径。
   *
   * @see ResourceUtils#applyRelativePath(String, String)
   */
  @Override
  public Resource createRelative(final String relativePath) {
    final String pathToUse = applyRelativePath(path, relativePath);
    if (file != null) {
      return new FileSystemResource(pathToUse);
    } else {
      return new FileSystemResource(filePath.getFileSystem(), pathToUse);
    }
  }

  /**
   * 此实现返回文件名。
   *
   * @see File#getName()
   * @see Path#getFileName()
   */
  @Override
  public String getFilename() {
    return (file != null ? file.getName() : filePath.getFileName().toString());
  }

  /**
   * 此实现返回一个包含文件绝对路径的描述。
   *
   * @see File#getAbsolutePath()
   * @see Path#toAbsolutePath()
   */
  @Override
  public String getDescription() {
    return "file [" + (file != null ? file.getAbsolutePath() : filePath.toAbsolutePath()) + "]";
  }

  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if ((o == null) || (getClass() != o.getClass())) {
      return false;
    }
    final FileSystemResource other = (FileSystemResource) o;
    return Equality.equals(path, other.path);
  }

  public int hashCode() {
    return path.hashCode();
  }

  public String toString() {
    return new ToStringBuilder(this)
        .append("path", path)
        .append("file", file)
        .append("filePath", filePath)
        .toString();
  }
}