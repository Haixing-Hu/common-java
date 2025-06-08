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
 * 抛出此异常以指示无法写入目录。
 *
 * @author 胡海星
 */
public class DirectoryCannotWriteException extends IOException
    implements ErrorInfoConvertable {

  @Serial
  private static final long serialVersionUID = 1028954470907555494L;

  private final String path;

  /**
   * 构造一个 {@link DirectoryCannotWriteException}。
   */
  public DirectoryCannotWriteException() {
    super("The directory can't be written to. ");
    this.path = "<unknown>";
  }

  /**
   * 构造一个 {@link DirectoryCannotWriteException}。
   *
   * @param path
   *     指定的目录路径。
   */
  public DirectoryCannotWriteException(final String path) {
    super("The directory can't be written to: " + path);
    this.path = path;
  }

  /**
   * 构造一个 {@link DirectoryCannotWriteException}。
   *
   * @param file
   *     指定的目录。
   */
  public DirectoryCannotWriteException(final File file) {
    this(file.getAbsolutePath());
  }

  /**
   * 构造一个 {@link DirectoryCannotWriteException}。
   *
   * @param path
   *     指定的目录路径。
   */
  public DirectoryCannotWriteException(final Path path) {
    this(path.toAbsolutePath().toString());
  }

  /**
   * 获取此异常相关的目录路径。
   *
   * @return
   *     此异常相关的目录路径。
   */
  public String getPath() {
    return path;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public ErrorInfo toErrorInfo() {
    return new ErrorInfo("IO_ERROR", "DIRECTORY_CANNOT_WRITE",
        KeyValuePairList.of("path", path, "reason", getMessage()));
  }
}