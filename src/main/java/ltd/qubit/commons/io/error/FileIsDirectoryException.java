////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////
package ltd.qubit.commons.io.error;

import java.io.File;
import java.io.IOException;
import java.io.Serial;
import java.nio.file.Path;

import ltd.qubit.commons.error.ErrorInfo;
import ltd.qubit.commons.error.ErrorInfoConvertable;
import ltd.qubit.commons.util.pair.KeyValuePairList;

/**
 * 抛出此异常表示文件存在，但它是一个目录而不是文件。
 *
 * @author 胡海星
 */
public class FileIsDirectoryException extends IOException
    implements ErrorInfoConvertable {

  @Serial
  private static final long serialVersionUID = -5140786141865269895L;

  private final String path;

  /**
   * 构造一个 {@link FileIsDirectoryException}。
   */
  public FileIsDirectoryException() {
    super("The file exists but is a directory. ");
    this.path = "<unknown>";
  }

  /**
   * 构造一个 {@link FileIsDirectoryException}。
   *
   * @param path
   *     作为目录的文件的路径。
   */
  public FileIsDirectoryException(final String path) {
    super("The file exists but is a directory: " + path);
    this.path = path;
  }

  /**
   * 构造一个 {@link FileIsDirectoryException}。
   *
   * @param file
   *     作为目录的文件。
   */
  public FileIsDirectoryException(final File file) {
    this(file.getAbsolutePath());
  }

  /**
   * 构造一个 {@link FileIsDirectoryException}。
   *
   * @param path
   *     作为目录的文件的路径。
   */
  public FileIsDirectoryException(final Path path) {
    this(path.toAbsolutePath().toString());
  }

  /**
   * 获取作为目录的文件的路径。
   *
   * @return 作为目录的文件的路径。
   */
  public String getPath() {
    return path;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public ErrorInfo toErrorInfo() {
    return new ErrorInfo("IO_ERROR", "FILE_IS_DIRECTORY",
        KeyValuePairList.of("path", path, "reason", getMessage()));
  }
}