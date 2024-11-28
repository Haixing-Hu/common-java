////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.io.io.resource;

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

import static ltd.qubit.commons.io.io.resource.ResourceUtils.URL_PROTOCOL_FILE;
import static ltd.qubit.commons.io.io.resource.ResourceUtils.applyRelativePath;
import static ltd.qubit.commons.io.io.resource.ResourceUtils.cleanPath;

/**
 * {@link Resource} implementation for {@code java.io.File} and
 * {@code java.nio.file.Path} handles with a file system target. Supports
 * resolution as a {@code File} and also as a {@code URL}. Implements the
 * extended {@link WritableResource} interface.
 * <p>
 * Note: As of Spring Framework 5.0, this {@link Resource} implementation uses
 * NIO.2 API for read/write interactions. As of 5.1, it may be constructed with
 * a {@link Path} handle in which case it will perform all file system
 * interactions via NIO.2, only resorting to {@link File} on
 * {@link #getFile()}.
 * <p>
 * This class is a copy of
 * {@code org.springframework.core.io.FileSystemResource} with slight
 * modifications. It is used to avoid the dependency of Spring Framework.
 *
 * @author Juergen Hoeller
 * @author Sam Brannen
 * @author Haixing Hu
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
   * Create a new {@code FileSystemResource} from a file path.
   * <p>
   * Note: When building relative resources via {@link #createRelative},
   * it makes a difference whether the specified resource base path here ends
   * with a slash or not. In the case of "C:/dir1/", relative paths will be
   * built underneath that root: e.g. relative path "dir2" &rarr;
   * "C:/dir1/dir2". In the case of "C:/dir1", relative paths will apply at the
   * same directory level: relative path "dir2" &rarr; "C:/dir2".
   *
   * @param path
   *     a file path
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
   * Create a new {@code FileSystemResource} from a {@link File} handle.
   * <p>
   * Note: When building relative resources via {@link #createRelative},
   * the relative path will apply <i>at the same directory level</i>: e.g. new
   * File("C:/dir1"), relative path "dir2" &rarr; "C:/dir2"! If you prefer to
   * have relative paths built underneath the given root directory, use the
   * {@link #FileSystemResource(String) constructor with a file path} to append
   * a trailing slash to the root path: "C:/dir1/", which indicates this
   * directory as root for all relative paths.
   *
   * @param file
   *     a File handle
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
   * Create a new {@code FileSystemResource} from a {@link Path} handle,
   * performing all file system interactions via NIO.2 instead of {@link File}.
   * <p>
   * In contrast to {@link PathResource}, this variant strictly follows the
   * general {@link FileSystemResource} conventions, in particular in terms of
   * path cleaning and {@link #createRelative(String)} handling.
   * <p>
   * Note: When building relative resources via {@link #createRelative}, the
   * relative path will apply <i>at the same directory level</i>: e.g.
   * Paths.get("C:/dir1"), relative path "dir2" &rarr; "C:/dir2"! If you prefer
   * to have relative paths built underneath the given root directory, use the
   * {@link #FileSystemResource(String) constructor with a file path} to append
   * a trailing slash to the root path: "C:/dir1/", which indicates this
   * directory as root for all relative paths. Alternatively, consider using
   * {@link PathResource#PathResource(Path)} for {@code java.nio.path.Path}
   * resolution in {@code createRelative}, always nesting relative paths.
   *
   * @param filePath
   *     a Path handle to a file
   * @see #FileSystemResource(File)
   * @since 5.1
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
   * Create a new {@code FileSystemResource} from a {@link FileSystem} handle,
   * locating the specified path.
   * <p>
   * This is an alternative to {@link #FileSystemResource(String)}, performing
   * all file system interactions via NIO.2 instead of {@link File}.
   *
   * @param fileSystem
   *     the FileSystem to locate the path within
   * @param path
   *     a file path
   * @see #FileSystemResource(File)
   * @since 5.1.1
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
   * Return the file path for this resource.
   */
  public final String getPath() {
    return this.path;
  }

  /**
   * This implementation returns whether the underlying file exists.
   *
   * @see File#exists()
   * @see Files#exists(Path, LinkOption...)
   */
  @Override
  public boolean exists() {
    return (this.file != null ? this.file.exists() : Files.exists(this.filePath));
  }

  /**
   * This implementation checks whether the underlying file is marked as
   * readable (and corresponds to an actual file with content, not to a
   * directory).
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
   * This implementation opens an NIO file stream for the underlying file.
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

  @Override
  public byte[] getContentAsByteArray() throws IOException {
    try {
      return Files.readAllBytes(this.filePath);
    } catch (final NoSuchFileException ex) {
      throw new FileNotFoundException(ex.getMessage());
    }
  }

  @Override
  public String getContentAsString(final Charset charset) throws IOException {
    try {
      return Files.readString(this.filePath, charset);
    } catch (final NoSuchFileException ex) {
      throw new FileNotFoundException(ex.getMessage());
    }
  }

  /**
   * This implementation checks whether the underlying file is marked as
   * writable (and corresponds to an actual file with content, not to a
   * directory).
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
   * This implementation opens a FileOutputStream for the underlying file.
   *
   * @see Files#newOutputStream(Path, OpenOption...)
   */
  @Override
  public OutputStream getOutputStream() throws IOException {
    return Files.newOutputStream(this.filePath);
  }

  /**
   * This implementation returns a URL for the underlying file.
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
   * This implementation returns a URI for the underlying file.
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
   * This implementation always indicates a file.
   */
  @Override
  public boolean isFile() {
    return true;
  }

  /**
   * This implementation returns the underlying File reference.
   */
  @Override
  public File getFile() {
    return (file != null ? file : filePath.toFile());
  }

  /**
   * This implementation opens a FileChannel for the underlying file.
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
   * This implementation opens a FileChannel for the underlying file.
   *
   * @see FileChannel
   */
  @Override
  public WritableByteChannel writableChannel() throws IOException {
    return FileChannel.open(filePath, StandardOpenOption.WRITE);
  }

  /**
   * This implementation returns the underlying File/Path length.
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
   * This implementation returns the underlying File/Path last-modified time.
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
   * This implementation creates a FileSystemResource, applying the given path
   * relative to the path of the underlying file of this resource descriptor.
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
   * This implementation returns the name of the file.
   *
   * @see File#getName()
   * @see Path#getFileName()
   */
  @Override
  public String getFilename() {
    return (file != null ? file.getName() : filePath.getFileName().toString());
  }

  /**
   * This implementation returns a description that includes the absolute path
   * of the file.
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
