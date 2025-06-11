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
 * 抛出此异常表示无法移动文件、目录或设备。
 *
 * @author 胡海星
 */
public class FileCannotMoveException extends IOException
    implements ErrorInfoConvertable {

  @Serial
  private static final long serialVersionUID = 8721109996158945749L;

  private final String path;

  /**
   * 构造一个 {@link FileCannotMoveException}。
   */
  public FileCannotMoveException() {
    super("The file can't be moved. ");
    this.path = "<unknown>";
  }

  /**
   * 使用指定的路径构造一个新的 {@link FileCannotMoveException}。
   *
   * @param path
   *     无法移动的文件的路径。
   */
  public FileCannotMoveException(final String path) {
    super("The file can't be moved: " + path);
    this.path = path;
  }

  /**
   * 使用指定的 {@link File} 对象构造一个新的 {@link FileCannotMoveException}。
   *
   * @param file
   *     无法移动的文件。
   */
  public FileCannotMoveException(final File file) {
    this(file.getAbsolutePath());
  }

  /**
   * 使用指定的 {@link Path} 对象构造一个新的 {@link FileCannotMoveException}。
   *
   * @param path
   *     无法移动的文件路径。
   */
  public FileCannotMoveException(final Path path) {
    this(path.toAbsolutePath().toString());
  }

  /**
   * 获取无法移动的文件的路径。
   *
   * @return 无法移动的文件的路径。
   */
  public final String getPath() {
    return path;
  }

  @Override
  public ErrorInfo toErrorInfo() {
    return new ErrorInfo("IO_ERROR", "FILE_CANNOT_MOVE",
        KeyValuePairList.of("path", path, "reason", getMessage()));
  }
}