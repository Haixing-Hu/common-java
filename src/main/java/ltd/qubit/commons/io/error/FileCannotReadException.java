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
 * 表示无法读取文件、目录或设备的异常。
 *
 * @author 胡海星
 */
public class FileCannotReadException extends IOException
    implements ErrorInfoConvertable {

  @Serial
  private static final long serialVersionUID = - 4170967684441144089L;

  private final String path;

  /**
   * 构造一个 {@link FileCannotReadException}。
   */
  public FileCannotReadException() {
    super("The file can't be read. ");
    this.path = "<unknown>";
  }

  /**
   * 构造一个 {@link FileCannotReadException}。
   *
   * @param path
   *     无法读取的文件的路径。
   */
  public FileCannotReadException(final String path) {
    super("The file can't be read: " + path);
    this.path = path;
  }

  /**
   * 构造一个 {@link FileCannotReadException}。
   *
   * @param file
   *     无法读取的文件。
   */
  public FileCannotReadException(final File file) {
    this(file.getAbsolutePath());
  }

  /**
   * 构造一个 {@link FileCannotReadException}。
   *
   * @param path
   *     无法读取的文件的路径。
   */
  public FileCannotReadException(final Path path) {
    this(path.toAbsolutePath().toString());
  }

  /**
   * 获取此异常相关联的路径。
   *
   * @return 此异常相关联的路径。
   */
  public String getPath() {
    return path;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public ErrorInfo toErrorInfo() {
    return new ErrorInfo("IO_ERROR", "FILE_CANNOT_READ",
        KeyValuePairList.of("path", path, "reason", getMessage()));
  }
}