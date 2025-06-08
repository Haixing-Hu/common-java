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
 * 抛出此异常以指示无法列出目录中的文件。
 *
 * @author 胡海星
 */
public class DirectoryCannotListException extends IOException
    implements ErrorInfoConvertable {

  @Serial
  private static final long serialVersionUID = 4992858511247895491L;

  private final String path;

  /**
   * 构造一个 {@link DirectoryCannotListException}。
   */
  public DirectoryCannotListException() {
    super("Can't list the directory. ");
    this.path = "<unknown>";
  }

  /**
   * 构造一个 {@link DirectoryCannotListException}。
   *
   * @param path
   *     指定的目录路径。
   */
  public DirectoryCannotListException(final String path) {
    super("Can't list the directory: " + path);
    this.path = path;
  }

  /**
   * 构造一个 {@link DirectoryCannotListException}。
   *
   * @param file
   *     指定的目录。
   */
  public DirectoryCannotListException(final File file) {
    this(file.getAbsolutePath());
  }

  /**
   * 构造一个 {@link DirectoryCannotListException}。
   *
   * @param path
   *     指定的目录路径。
   */
  public DirectoryCannotListException(final Path path) {
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
    return new ErrorInfo("IO_ERROR", "DIRECTORY_CANNOT_LIST",
        KeyValuePairList.of("path", path, "reason", getMessage()));
  }
}