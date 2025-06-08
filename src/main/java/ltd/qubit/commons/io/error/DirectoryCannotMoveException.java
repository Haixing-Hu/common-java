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
 * 抛出此异常以指示无法移动目录。
 *
 * @author 胡海星
 */
public class DirectoryCannotMoveException extends IOException
    implements ErrorInfoConvertable {

  @Serial
  private static final long serialVersionUID = 5344978076308643042L;

  private final String path;

  /**
   * 构造一个 {@link DirectoryCannotMoveException}。
   */
  public DirectoryCannotMoveException() {
    super("The directory can't be moved. ");
    this.path = "<unknown>";
  }

  /**
   * 构造一个 {@link DirectoryCannotMoveException}。
   *
   * @param path
   *     指定的目录路径。
   */
  public DirectoryCannotMoveException(final String path) {
    super("The directory can't be moved: " + path);
    this.path = path;
  }

  /**
   * 构造一个 {@link DirectoryCannotMoveException}。
   *
   * @param file
   *     指定的目录。
   */
  public DirectoryCannotMoveException(final File file) {
    this(file.getAbsolutePath());
  }

  /**
   * 构造一个 {@link DirectoryCannotMoveException}。
   *
   * @param path
   *     指定的目录路径。
   */
  public DirectoryCannotMoveException(final Path path) {
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
    return new ErrorInfo("IO_ERROR", "DIRECTORY_CANNOT_MOVE",
        KeyValuePairList.of("path", path, "reason", getMessage()));
  }
}