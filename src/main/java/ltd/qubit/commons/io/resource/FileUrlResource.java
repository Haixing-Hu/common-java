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
import java.io.IOException;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.FileChannel;
import java.nio.channels.WritableByteChannel;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;

import javax.annotation.Nullable;

/**
 * Subclass of {@link UrlResource} which assumes file resolution, to the degree
 * of implementing the {@link WritableResource} interface for it. This resource
 * variant also caches resolved {@link File} handles from {@link #getFile()}.
 * <p>
 * This is the class resolved by {@link DefaultResourceLoader} for a "file:..."
 * URL location, allowing a downcast to {@link WritableResource} for it.
 * <p>
 * Alternatively, for direct construction from a {@link File} handle or
 * NIO {@link java.nio.file.Path}, consider using {@link FileSystemResource}.
 * <p>
 * This class is a copy of {@code org.springframework.core.io.FileUrlResource}
 * with slight modifications. It is used to avoid the dependency of Spring
 * Framework.
 *
 * @author Juergen Hoeller
 * @author Haixing Hu
 */
public class FileUrlResource extends UrlResource implements WritableResource {

  @Nullable
  private volatile File file;

  /**
   * Create a new {@code FileUrlResource} based on the given URL object.
   * <p>
   * Note that this does not enforce "file" as URL protocol. If a protocol
   * is known to be resolvable to a file, it is acceptable for this purpose.
   *
   * @param url
   *     a URL
   * @see org.springframework.util.ResourceUtils#isFileURL(URL)
   * @see #getFile()
   */
  public FileUrlResource(final URL url) {
    super(url);
  }

  /**
   * Create a new {@code FileUrlResource} based on the given file location,
   * using the URL protocol "file".
   * <p>
   * The given parts will automatically get encoded if necessary.
   *
   * @param location
   *     the location (i.e. the file path within that protocol)
   * @throws MalformedURLException
   *     if the given URL specification is not valid
   * @see UrlResource#UrlResource(String, String)
   * @see org.springframework.util.ResourceUtils#URL_PROTOCOL_FILE
   */
  public FileUrlResource(final String location) throws MalformedURLException {
    super(ResourceUtils.URL_PROTOCOL_FILE, location);
  }

  @Override
  public File getFile() throws IOException {
    File theFile = this.file;
    if (theFile != null) {
      return theFile;
    }
    theFile = super.getFile();
    this.file = theFile;
    return theFile;
  }

  @Override
  public boolean isWritable() {
    try {
      final File theFile = getFile();
      return (theFile.canWrite() && !theFile.isDirectory());
    } catch (final IOException ex) {
      return false;
    }
  }

  @Override
  public OutputStream getOutputStream() throws IOException {
    return Files.newOutputStream(getFile().toPath());
  }

  @Override
  public WritableByteChannel writableChannel() throws IOException {
    return FileChannel.open(getFile().toPath(), StandardOpenOption.WRITE);
  }

  @Override
  public Resource createRelative(final String relativePath)
      throws MalformedURLException {
    return new FileUrlResource(createRelativeURL(relativePath));
  }

}
