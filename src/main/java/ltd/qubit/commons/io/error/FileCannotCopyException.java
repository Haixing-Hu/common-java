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
 * 表示无法复制文件、目录或设备的异常。
 *
 * @author 胡海星
 */
public class FileCannotCopyException extends IOException
    implements ErrorInfoConvertable {

  @Serial
  private static final long serialVersionUID = 6900269932475719771L;

  private final String path;

  /**
   * 构造一个 {@link FileCannotCopyException}。
   */
  public FileCannotCopyException() {
    super("The file can't be copied. ");
    this.path = "<unknown>";
  }

  /**
   * 使用指定的路径构造一个新的 {@link FileCannotCopyException}。
   *
   * @param path
   *     无法复制的文件的路径。
   */
  public FileCannotCopyException(final String path) {
    super("The file can't be copied: " + path);
    this.path = path;
  }

  /**
   * 使用指定的 {@link File} 对象构造一个新的 {@link FileCannotCopyException}。
   *
   * @param file
   *     无法复制的文件。
   */
  public FileCannotCopyException(final File file) {
    this(file.getAbsolutePath());
  }

  /**
   * 使用指定的 {@link Path} 对象构造一个新的 {@link FileCannotCopyException}。
   *
   * @param path
   *     无法复制的文件路径。
   */
  public FileCannotCopyException(final Path path) {
    this(path.toAbsolutePath().toString());
  }

  /**
   * 获取无法复制的文件的路径。
   *
   * @return 无法复制的文件的路径。
   */
  public final String getPath() {
    return path;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public ErrorInfo toErrorInfo() {
    return new ErrorInfo("IO_ERROR", "FILE_CANNOT_COPY",
        KeyValuePairList.of("path", path, "reason", getMessage()));
  }
}