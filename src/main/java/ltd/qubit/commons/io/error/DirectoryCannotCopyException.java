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
 * 抛出此异常以指示无法复制目录。
 *
 * @author 胡海星
 */
public class DirectoryCannotCopyException extends IOException
    implements ErrorInfoConvertable {

  @Serial
  private static final long serialVersionUID = 1869539087762563069L;

  private final String path;

  /**
   * 构造一个表示无法复制目录的异常。
   */
  public DirectoryCannotCopyException() {
    super("The directory can't be copied. ");
    this.path = "<unknown>";
  }

  /**
   * 构造一个表示无法复制目录的异常。
   *
   * @param path
   *     目录路径。
   */
  public DirectoryCannotCopyException(final String path) {
    super("The directory can't be copied: " + path);
    this.path = path;
  }

  /**
   * 构造一个表示无法复制目录的异常。
   *
   * @param file
   *     目录文件。
   */
  public DirectoryCannotCopyException(final File file) {
    this(file.getAbsolutePath());
  }

  /**
   * 构造一个表示无法复制目录的异常。
   *
   * @param path
   *     目录路径。
   */
  public DirectoryCannotCopyException(final Path path) {
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
    return new ErrorInfo("IO_ERROR", "DIRECTORY_CANNOT_COPY",
        KeyValuePairList.of("path", path, "reason", getMessage()));
  }
}