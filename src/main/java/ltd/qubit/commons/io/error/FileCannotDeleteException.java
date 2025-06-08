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
 * 表示无法删除文件、目录或设备的异常。
 *
 * @author 胡海星
 */
public class FileCannotDeleteException extends IOException
    implements ErrorInfoConvertable {

  @Serial
  private static final long serialVersionUID = - 7495925551285474239L;

  private final String path;

  /**
   * 构造一个 {@link FileCannotDeleteException}。
   */
  public FileCannotDeleteException() {
    super("The file can't be deleted. ");
    this.path = "<unknown>";
  }

  /**
   * 使用指定的路径构造一个新的 {@link FileCannotDeleteException}。
   *
   * @param path
   *     无法删除的文件的路径。
   */
  public FileCannotDeleteException(final String path) {
    super("The file can't be deleted: " + path);
    this.path = path;
  }

  /**
   * 使用指定的 {@link File} 对象构造一个新的 {@link FileCannotDeleteException}。
   *
   * @param file
   *     无法删除的文件。
   */
  public FileCannotDeleteException(final File file) {
    this(file.getAbsolutePath());
  }

  /**
   * 使用指定的 {@link Path} 对象构造一个新的 {@link FileCannotDeleteException}。
   *
   * @param path
   *     无法删除的文件路径。
   */
  public FileCannotDeleteException(final Path path) {
    this(path.toAbsolutePath().toString());
  }

  /**
   * 获取无法删除的文件的路径。
   *
   * @return 无法删除的文件的路径。
   */
  public String getPath() {
    return path;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public ErrorInfo toErrorInfo() {
    return new ErrorInfo("IO_ERROR", "FILE_CANNOT_DELETE",
        KeyValuePairList.of("path", path, "reason", getMessage()));
  }
}