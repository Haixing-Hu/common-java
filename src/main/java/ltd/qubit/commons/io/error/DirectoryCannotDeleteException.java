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
 * 抛出此异常以指示无法删除目录。
 *
 * @author 胡海星
 */
public class DirectoryCannotDeleteException extends IOException
    implements ErrorInfoConvertable {

  @Serial
  private static final long serialVersionUID = 568544508014410954L;

  private final String path;

  /**
   * 构造一个表示无法删除目录的异常。
   */
  public DirectoryCannotDeleteException() {
    super("The directory can't be deleted. ");
    this.path = "<unknown>";
  }

  /**
   * 构造一个表示无法删除目录的异常。
   *
   * @param path
   *     目录路径。
   */
  public DirectoryCannotDeleteException(final String path) {
    super("The directory can't be deleted: " + path);
    this.path = path;
  }

  /**
   * 构造一个表示无法删除目录的异常。
   *
   * @param file
   *     目录文件。
   */
  public DirectoryCannotDeleteException(final File file) {
    this(file.getAbsolutePath());
  }

  /**
   * 构造一个表示无法删除目录的异常。
   *
   * @param path
   *     目录路径。
   */
  public DirectoryCannotDeleteException(final Path path) {
    this(path.toAbsolutePath().toString());
  }

  /**
   * 获取目录路径。
   *
   * @return
   *     目录路径。
   */
  public String getPath() {
    return path;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public ErrorInfo toErrorInfo() {
    return new ErrorInfo("IO_ERROR", "DIRECTORY_CANNOT_DELETE",
        KeyValuePairList.of("path", path, "reason", getMessage()));
  }
}