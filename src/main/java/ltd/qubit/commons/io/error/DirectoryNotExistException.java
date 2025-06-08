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
import java.io.FileNotFoundException;
import java.io.Serial;
import java.nio.file.Path;

import ltd.qubit.commons.error.ErrorInfo;
import ltd.qubit.commons.error.ErrorInfoConvertable;
import ltd.qubit.commons.util.pair.KeyValuePairList;

/**
 * 抛出此异常以指示目录不存在。
 *
 * @author 胡海星
 */
public class DirectoryNotExistException extends FileNotFoundException
    implements ErrorInfoConvertable {

  @Serial
  private static final long serialVersionUID = - 8781993099750916914L;

  private final String path;

  /**
   * 构造一个 {@link DirectoryNotExistException}。
   */
  public DirectoryNotExistException() {
    super("Can't find the directory. ");
    this.path = "<unknown>";
  }

  /**
   * 构造一个 {@link DirectoryNotExistException}。
   *
   * @param path
   *     指定的目录路径。
   */
  public DirectoryNotExistException(final String path) {
    super("Can't find the directory: " + path);
    this.path = path;
  }

  /**
   * 构造一个 {@link DirectoryNotExistException}。
   *
   * @param file
   *     指定的目录。
   */
  public DirectoryNotExistException(final File file) {
    this(file.getAbsolutePath());
  }

  /**
   * 构造一个 {@link DirectoryNotExistException}。
   *
   * @param path
   *     指定的目录路径。
   */
  public DirectoryNotExistException(final Path path) {
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
    return new ErrorInfo("IO_ERROR", "DIRECTORY_NOT_EXIST",
        KeyValuePairList.of("path", path, "reason", getMessage()));
  }
}