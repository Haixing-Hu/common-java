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
 * 抛出此异常以指示无法读取目录。
 *
 * @author 胡海星
 */
public class DirectoryCannotReadException extends IOException
    implements ErrorInfoConvertable {

  @Serial
  private static final long serialVersionUID = 4992858511247895491L;

  private final String path;

  /**
   * 构造一个 {@link DirectoryCannotReadException}。
   */
  public DirectoryCannotReadException() {
    super("Can't read the directory. ");
    this.path = "<unknown>";
  }

  /**
   * 构造一个 {@link DirectoryCannotReadException}。
   *
   * @param path
   *     指定的目录路径。
   */
  public DirectoryCannotReadException(final String path) {
    super("Can't read the directory: " + path);
    this.path = path;
  }

  /**
   * 构造一个 {@link DirectoryCannotReadException}。
   *
   * @param file
   *     指定的目录。
   */
  public DirectoryCannotReadException(final File file) {
    this(file.getAbsolutePath());
  }

  /**
   * 构造一个 {@link DirectoryCannotReadException}。
   *
   * @param path
   *     指定的目录路径。
   */
  public DirectoryCannotReadException(final Path path) {
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
    return new ErrorInfo("IO_ERROR", "DIRECTORY_CANNOT_READ",
        KeyValuePairList.of("path", path, "reason", getMessage()));
  }
}