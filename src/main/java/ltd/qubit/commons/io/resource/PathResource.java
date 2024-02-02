////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
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
 * {@link Resource} implementation for {@link Path} handles, performing all
 * operations and transformations via the {@code Path} API. Supports resolution
 * as a {@link File} and also as a {@link URL}. Implements the extended
 * {@link WritableResource} interface.
 * <p>
 * Note: As of 5.1, {@link Path} support is also available in
 * {@link FileSystemResource#FileSystemResource(Path) FileSystemResource},
 * applying Spring's standard String-based path transformations but performing
 * all operations via the {@link Files} API. This {@code PathResource} is
 * effectively a pure {@code java.nio.path.Path} based alternative with
 * different {@code createRelative} behavior.
 * <p>
 * This class is a copy of {@code org.springframework.core.io.PathResource} with
 * slight modifications. It is used to avoid the dependency of Spring
 * Framework.
 *
 * @author Philippe Marschall
 * @author Juergen Hoeller
 * @author Haixing Hu
 * @see Path
 * @see Files
 * @see FileSystemResource
 * @since 4.0
 */
public class PathResource extends AbstractResource implements WritableResource {

  private final Path path;

  /**
   * Create a new {@code PathResource} from a {@link Path} handle.
   * <p>
   * Note: Unlike {@link FileSystemResource}, when building relative resources
   * via {@link #createRelative}, the relative path will be built
   * <i>underneath</i> the given root: e.g. Paths.get("C:/dir1/"), relative
   * path
   * "dir2" &rarr; "C:/dir1/dir2"!
   *
   * @param path
   *     a Path handle
   */
  public PathResource(final Path path) {
    if (path == null) {
      throw new IllegalArgumentException("Path must not be null");
    }
    this.path = path.normalize();
  }

  /**
   * Create a new {@code PathResource} from a path string.
   * <p>
   * Note: Unlike {@link FileSystemResource}, when building relative resources
   * via {@link #createRelative}, the relative path will be built
   * <i>underneath</i> the given root: e.g. Paths.get("C:/dir1/"), relative
   * path
   * "dir2" &rarr; "C:/dir1/dir2"!
   *
   * @param path
   *     a path
   * @see Paths#get(String, String...)
   */
  public PathResource(final String path) {
    if (path == null) {
      throw new IllegalArgumentException("Path must not be null");
    }
    this.path = Paths.get(path).normalize();
  }

  /**
   * Create a new {@code PathResource} from a {@link URI}.
   * <p>
   * Note: Unlike {@link FileSystemResource}, when building relative resources
   * via {@link #createRelative}, the relative path will be built
   * <i>underneath</i> the given root: e.g. Paths.get("C:/dir1/"), relative
   * path
   * "dir2" &rarr; "C:/dir1/dir2"!
   *
   * @param uri
   *     a path URI
   * @see Paths#get(URI)
   */
  public PathResource(final URI uri) {
    if (uri == null) {
      throw new IllegalArgumentException("URI must not be null");
    }
    path = Paths.get(uri).normalize();
  }


  /**
   * Return the file path for this resource.
   */
  public final String getPath() {
    return path.toString();
  }

  /**
   * This implementation returns whether the underlying file exists.
   *
   * @see Files#exists(Path, LinkOption...)
   */
  @Override
  public boolean exists() {
    return Files.exists(path);
  }

  /**
   * This implementation checks whether the underlying file is marked as
   * readable (and corresponds to an actual file with content, not to a
   * directory).
   *
   * @see Files#isReadable(Path)
   * @see Files#isDirectory(Path, LinkOption...)
   */
  @Override
  public boolean isReadable() {
    return (Files.isReadable(path) && !Files.isDirectory(path));
  }

  /**
   * This implementation opens an {@link InputStream} for the underlying file.
   *
   * @see FileSystemProvider#newInputStream(Path,
   *     OpenOption...)
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

  @Override
  public byte[] getContentAsByteArray() throws IOException {
    try {
      return Files.readAllBytes(path);
    } catch (final NoSuchFileException ex) {
      throw new FileNotFoundException(ex.getMessage());
    }
  }

  @Override
  public String getContentAsString(final Charset charset) throws IOException {
    try {
      return Files.readString(path, charset);
    } catch (final NoSuchFileException ex) {
      throw new FileNotFoundException(ex.getMessage());
    }
  }

  /**
   * This implementation checks whether the underlying file is marked as
   * writable (and corresponds to an actual file with content, not to a
   * directory).
   *
   * @see Files#isWritable(Path)
   * @see Files#isDirectory(Path, LinkOption...)
   */
  @Override
  public boolean isWritable() {
    return (Files.isWritable(path) && !Files.isDirectory(path));
  }

  /**
   * This implementation opens an {@link OutputStream} for the underlying file.
   *
   * @see FileSystemProvider#newOutputStream(Path,
   *     OpenOption...)
   */
  @Override
  public OutputStream getOutputStream() throws IOException {
    if (Files.isDirectory(path)) {
      throw new FileNotFoundException(getPath() + " (is a directory)");
    }
    return Files.newOutputStream(path);
  }

  /**
   * This implementation returns a {@link URL} for the underlying file.
   *
   * @see Path#toUri()
   * @see URI#toURL()
   */
  @Override
  public URL getURL() throws IOException {
    return path.toUri().toURL();
  }

  /**
   * This implementation returns a {@link URI} for the underlying file.
   *
   * @see Path#toUri()
   */
  @Override
  public URI getURI() throws IOException {
    return path.toUri();
  }

  /**
   * This implementation always indicates a file.
   */
  @Override
  public boolean isFile() {
    return true;
  }

  /**
   * This implementation returns the underlying {@link File} reference.
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
   * This implementation opens a {@link ReadableByteChannel} for the underlying
   * file.
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
   * This implementation opens a {@link WritableByteChannel} for the underlying
   * file.
   *
   * @see Files#newByteChannel(Path, OpenOption...)
   */
  @Override
  public WritableByteChannel writableChannel() throws IOException {
    return Files.newByteChannel(this.path, StandardOpenOption.WRITE);
  }

  /**
   * This implementation returns the underlying file's length.
   */
  @Override
  public long contentLength() throws IOException {
    return Files.size(this.path);
  }

  /**
   * This implementation returns the underlying file's timestamp.
   *
   * @see Files#getLastModifiedTime(Path,
   *     LinkOption...)
   */
  @Override
  public long lastModified() throws IOException {
    // We can not use the superclass method since it uses conversion to a File and
    // only a Path on the default file system can be converted to a File...
    return Files.getLastModifiedTime(this.path).toMillis();
  }

  /**
   * This implementation creates a {@link PathResource}, applying the given path
   * relative to the path of the underlying file of this resource descriptor.
   *
   * @see Path#resolve(String)
   */
  @Override
  public Resource createRelative(final String relativePath) {
    return new PathResource(path.resolve(relativePath));
  }

  /**
   * This implementation returns the name of the file.
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
