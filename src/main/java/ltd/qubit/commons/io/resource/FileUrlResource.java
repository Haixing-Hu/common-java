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
 * {@link UrlResource} 的子类，它假定进行文件解析，以至于为其实现了
 * {@link WritableResource} 接口。此资源变体还缓存从 {@link #getFile()}
 * 解析的 {@link File} 句柄。
 * <p>
 * 这是 {@link DefaultResourceLoader} 为 "file:..." URL 位置解析的类，允许对其
 * 向下转型为 {@link WritableResource}。
 * <p>
 * 或者，对于直接从 {@link java.io.File} 句柄或 NIO {@link java.nio.file.Path}
 * 构造，请考虑使用 {@link FileSystemResource}。
 * <p>
 * 此类是 {@code org.springframework.core.io.FileUrlResource} 的副本，经过少量修改。
 * 它用于避免对 Spring Framework 的依赖。
 *
 * @author 胡海星
 */
public class FileUrlResource extends UrlResource implements WritableResource {

  @Nullable
  private volatile File file;

  /**
   * 基于给定的 URL 对象创建一个新的 {@code FileUrlResource}。
   * <p>
   * 请注意，这并不强制 "file" 作为 URL 协议。如果已知某个协议可以解析为文件，
   * 那么它对于此目的是可接受的。
   *
   * @param url
   *     一个 URL。
   * @see ResourceUtils#isFileURL(URL)
   * @see #getFile()
   */
  public FileUrlResource(final URL url) {
    super(url);
  }

  /**
   * 基于给定的文件位置创建一个新的 {@code FileUrlResource}，使用 "file" URL 协议。
   * <p>
   * 如有必要，给定的部分将自动进行编码。
   *
   * @param location
   *     位置（即该协议内的文件路径）。
   * @throws MalformedURLException
   *     如果给定的 URL 规范无效。
   * @see UrlResource#UrlResource(String, String)
   * @see ResourceUtils#URL_PROTOCOL_FILE
   */
  public FileUrlResource(final String location) throws MalformedURLException {
    super(ResourceUtils.URL_PROTOCOL_FILE, location);
  }

  /**
   * {@inheritDoc}
   */
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

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isWritable() {
    try {
      final File theFile = getFile();
      return (theFile.canWrite() && !theFile.isDirectory());
    } catch (final IOException ex) {
      return false;
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public OutputStream getOutputStream() throws IOException {
    return Files.newOutputStream(getFile().toPath());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public WritableByteChannel writableChannel() throws IOException {
    return FileChannel.open(getFile().toPath(), StandardOpenOption.WRITE);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Resource createRelative(final String relativePath)
      throws MalformedURLException {
    return new FileUrlResource(createRelativeURL(relativePath));
  }

}